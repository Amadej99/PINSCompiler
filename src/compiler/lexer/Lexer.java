/**
 * @Author: turk
 * @Description: Leksikalni analizator.
 */

package compiler.lexer;

import static common.RequireNonNull.requireNonNull;
import static compiler.lexer.TokenType.*;

import java.util.ArrayList;
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

        String[] operators = {"+", "-", "*", "/", "%", "&", "|", "!", "==", "!=", "<", ">", "<=", ">=", "(", ")", "[",
                "]", "{", "}", ":", ";", ",", "=", "..."};
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

        // Inicializiram trenutni niz znakov
        String currentString = "";
        // Inicializiram zacetno vrstico in stolpec trenutnega niza znakov
        int startCol = 1;
        int startLine = 1;

        // Grem cez celoten program
        for (int i = 0; i < source.length(); i++) {
            // Trenutni znak, ki ga obravnavam
            char currentChar = source.charAt(i);

            // Ce je trenutni znak '#' in v trenutnem nizu ni zadetkov, potem preskocim
            // komentar
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

            // Ce je trenutni znak presledek ga preskocim
            if (currentString.equals(" ")) {
                currentString = "";
            }

            // Ce je trenutni znak tabulator, potem ga preskocim in povecam stolpec za 3
            if (currentString.equals("	")) {
                currentString = "";
                col += 3;
            }

            // Ce je trenutni niz prazen potem nastavim zacetek novega niza na trenutni
            // stopec in vrstico
            if (currentString.length() < 1) {
                startCol = col;
                startLine = line;
            }

            // Dodam trenutni znak v trenutni niz znakov
            currentString = addCharacter(currentString, currentChar);

            // Ce se trenutni niz ne ujema s katerimkoli simbolom, prejsnji pa se ujema,
            // potem dodam prejsnjega
            if (findMatch(currentString) == null && findMatch(backTrack(currentString)) != null) {
                symbols.add(
                        addSymbol(startLine, startCol, findMatch(backTrack(currentString)), backTrack(currentString)));
                // Nastavim trenutni niz na trenutno obraavnavani znak
                currentString = String.valueOf(currentChar);
                // Nastavim zacetni stolpec na stolpec trenutnega znaka
                startCol = col;
            }

            // Ce je trenutni znak LF, potem nastavim trenutni niz na prazen niz, povecam
            // vrstico in nastavim stolpec na 1
            if (currentChar == (char) 10) {
                currentString = "";
                line++;
                col = 1;
                continue;
            }
            col++;
        }

        if (currentString.length() > 0) {
            // Ce trenutni niz ni prazen in ni komentar, potem dodam simbol
            if (findMatch(backTrack(currentString)) == COMMENT || currentString.isBlank())
                currentString = "";
            else {
                symbols.add(addSymbol(startLine, startCol, findMatch(currentString), currentString));
            }
        }

        symbols.add(new Symbol(Position.zero(), EOF, "$"));

        return symbols;
    }

    // Dodaj znak v trenutni niz
    private String addCharacter(String currentString, char currentChar) {
        currentString += currentChar;
        return currentString;
    }

    // Preveri ali se trenutni niz ujema s katerimkoli simbolom in ga vrni
    private TokenType findMatch(String currentString) {
        // Ce je trenutni niz kljucna beseda
        if (keywordMapping.containsKey(currentString)) {
            return keywordMapping.get(currentString);
            // Ce je trenutni niz operator
        } else if (operatorMapping.containsKey(currentString)) {
            return operatorMapping.get(currentString);
        } else {
            // Ce je trenutni niz v regexu
            for (int i = 0; i < regexCheck.size(); i++) {
                if (regexCheck.get(i).matcher(currentString).find()) {
                    return regexMapping.get(regexCheck.get(i).toString());
                }
            }
        }

        return null;
    }

    // Preveri ali je v prejsnjem nizu simbol
    private String backTrack(String currentString) {
        return currentString.substring(0, currentString.length() - 1);
    }

    // '''neki''' == 'neki'
    private Symbol addSymbol(int startLine, int startCol, TokenType token, String currentString) {
        Position position = new Position(startLine, startCol, line, col);
        if (token == C_STRING) {
            if (currentString.equals("''")) {
                currentString = "";
            } else {
                int count = currentString.length() - currentString.replace("'", "").length();
                if (count % 2 != 0) {
                    // Ce je neparno stevilo apostrofov, potem je niz nezakljucen
                    Report.error("Napaka: nezakljucen niz!");
                } else {
                    // Ce je sodo stevilo apostrofov, potem zamenjam dvojne z enojnimi
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
