/**
 * @Author: turk
 * @Description: Leksikalni analizator.
 */

package compiler.lexer;

import static common.RequireNonNull.requireNonNull;
import static compiler.lexer.TokenType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import common.Report;

public class Lexer {
	/**
	 * Izvorna koda.
	 */
	private final String source;
	private int col = 1;
	private int line = 1;

	/**
	 * Preslikava iz ključnih besed v vrste simbolov.
	 */
	private final static Map<String, TokenType> keywordMapping;
	private final static Map<String, TokenType> operatorMapping;
	private final static Map<String, TokenType> regexMapping;
	private final static ArrayList<Pattern> regexCheck;

	static {
		keywordMapping = new HashMap<>();
		operatorMapping = new HashMap<>();
		for (var token : TokenType.values()) {
			var str = token.toString();
			if (str.startsWith("KW_")) {
				keywordMapping.put(str.substring("KW_".length()).toLowerCase(), token);
			}
			if (str.startsWith("AT_")) {
				keywordMapping.put(str.substring("AT_".length()).toLowerCase(), token);
			}
		}

		keywordMapping.put("integer", AT_INTEGER);
		keywordMapping.put("logical", AT_LOGICAL);
		keywordMapping.put("string", AT_STRING);

		String[] operators = { "+", "-", "*", "/", "%", "&", "|", "!", "==", "!=", "<", ">", "<=", ">=", "(", ")", "[",
				"]", "{", "}", ":", ";", ".", ",", "=" };
		List<TokenType> otherTokenTypes = new ArrayList<>();

		for (var token : TokenType.values()) {
			if (token.toString().startsWith("OP"))
				otherTokenTypes.add(token);
		}

		for (int i = 0; i < operators.length; i++) {
			operatorMapping.put(operators[i], otherTokenTypes.get(i));
		}

		regexMapping = new HashMap<>();
		regexMapping.put("^[0-9]+\\z", C_INTEGER);
		regexMapping.put("^(true|false)\\z", C_LOGICAL);
		regexMapping.put("^'(?<match>(?:[^']|'')*)'?\\z", C_STRING);
		regexMapping.put("^[a-zA-Z_]+[a-zA-Z0-9_]*\\z", IDENTIFIER);
		regexMapping.put("^#.*\\z", COMMENT);

		regexCheck = new ArrayList<>();
		for (Map.Entry<String, TokenType> entry : regexMapping.entrySet()) {
			regexCheck.add(Pattern.compile(entry.getKey()));
		}
	}

	/**
	 * Ustvari nov analizator.
	 * 
	 * @param source Izvorna koda programa.
	 */
	public Lexer(String source) {
		requireNonNull(source);
		this.source = source;
	}

	/**
	 * Izvedi leksikalno analizo.
	 * 
	 * @return seznam leksikalnih simbolov.
	 */
	public List<Symbol> scan() {

		var symbols = new ArrayList<Symbol>();

		// Errori

		String currentString = "";
		int startCol = 1;
		int startLine = 1;

		for (int i = 0; i < source.length(); i++) {
			char currentChar = source.charAt(i);

			if (currentChar == '#' && findMatch(currentString) == null) {
				while (source.charAt(i) != (char) 10 && i < source.length() - 1) {
					i++;
				}
				if (i == source.length() - 1) {
					currentString = "";
				}
				currentString = "";
				line++;
				col = 1;
				continue;
			}

			if (!String.valueOf(currentChar).matches("\\A\\p{ASCII}*\\z")) {
				Report.error("Neveljavni znak: " + currentChar);
			}

			if (currentString.equals(" ")) {
				currentString = "";
			}

			if (currentString.length() < 1) {
				startCol = col;
				startLine = line;
			}

			currentString = addCharacter(currentString, currentChar);

			if (findMatch(currentString) == null && findMatch(backTrack(currentString)) != null) {
				if (findMatch(backTrack(currentString)) == COMMENT) {
					currentString = "";
				} else {
					symbols.add(
							addSymbol(startLine, startCol, findMatch(backTrack(currentString)),
									backTrack(currentString)));
				}

				currentString = String.valueOf(currentChar);
				startCol = col;
			}

			if (currentChar == (char) 10) {
				currentString = "";
				line++;
				col = 1;
				continue;
			}
			col++;
		}

		if (currentString.equals(" ")) {
			currentString = "";
		}

		if (currentString.length() > 0) {
			if (findMatch(backTrack(currentString)) == COMMENT)
				currentString = "";
			else {
				symbols.add(addSymbol(startLine, startCol, findMatch(currentString), currentString));
			}
		}

		symbols.add(new Symbol(Position.zero(), EOF, "$"));

		for (int i = 0; i < symbols.size(); i++) {
			System.out.println(symbols.get(i));
		}

		return symbols;
	}

	private String addCharacter(String currentString, char currentChar) {
		currentString += currentChar;
		return currentString;
	}

	private TokenType findMatch(String currentString) {
		if (keywordMapping.containsKey(currentString)) {
			return keywordMapping.get(currentString);
		} else if (operatorMapping.containsKey(currentString)) {
			return operatorMapping.get(currentString);
		} else {
			for (int i = 0; i < regexCheck.size(); i++) {
				if (regexCheck.get(i).matcher(currentString).find()) {
					return regexMapping.get(regexCheck.get(i).toString());
				}
			}
		}

		return null;
	}

	private String backTrack(String currentString) {
		return currentString.substring(0, currentString.length() - 1);
	}

	private Symbol addSymbol(int startLine, int startCol, TokenType token, String currentString) {
		Position position = new Position(startLine, startCol, line, col);
		if (token == C_STRING) {
			if (currentString.equals("''")) {
				currentString = "";
			} else {
				int count = currentString.length() - currentString.replace("'", "").length();
				if (count % 2 != 0) {
					Report.error("Napaka: nezakljucen niz!");
				} else {
					currentString = currentString.replace("''", "'");
					currentString = currentString.substring(1, currentString.length() - 1);
				}
			}
		}
		if (token == null) {
			Report.error("Neprepoznan niz!");
		}
		return new Symbol(position, token, currentString);
	}
}
