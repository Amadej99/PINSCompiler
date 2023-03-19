/**
 * @Author: turk
 * @Description: Sintaksni analizator.
 */

package compiler.parser;

import static compiler.lexer.TokenType.*;
import static common.RequireNonNull.requireNonNull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.Report;
import compiler.lexer.Position;
import compiler.lexer.Symbol;
import compiler.lexer.Position.Location;
import compiler.parser.ast.Ast;
import compiler.parser.ast.def.Def;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.FunDef;
import compiler.parser.ast.def.TypeDef;
import compiler.parser.ast.def.VarDef;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.Type;
import compiler.parser.ast.type.TypeName;

public class Parser {
	/**
	 * Seznam leksikalnih simbolov.
	 */
	private final List<Symbol> symbols;
	private final Iterator si;

	/**
	 * Ciljni tok, kamor izpisujemo produkcije. Če produkcij ne želimo izpisovati,
	 * vrednost opcijske spremenljivke nastavimo na Optional.empty().
	 */
	private final Optional<PrintStream> productionsOutputStream;

	public Parser(List<Symbol> symbols, Optional<PrintStream> productionsOutputStream) {
		requireNonNull(symbols, productionsOutputStream);
		this.symbols = symbols;
		this.productionsOutputStream = productionsOutputStream;
		this.si = new Iterator(symbols);
	}

	/**
	 * Izvedi sintaksno analizo.
	 */
	public Ast parse() {
		return parseSource();
	}

	private Defs parseSource() {
		dump("source -> definitions");
		var defs = parseDefinitions();
		if (si.getNext().equals(EOF)) {
			return defs;
		} else {
			Report.error(si.getSymbol().position, "Pricakoval EOF");
			return null;
		}
	}

	Defs parseDefinitions() {
		dump("definitions -> definition definitions2");
		Location start = si.getSymbol().position.start;
		Def def = parseDefinition();
		List<Def> defsList = new ArrayList<>();
		Defs defs = parseDefinitions2();
		return new Defs(new Position(start, def.position.end), defsList);
	}

	Def parseDefinition() {
		if (si.getNext().equals(KW_TYP)) {
			dump("definition -> type_definition");
			return parseTypeDefinition();
		} else if (si.getNext().equals(KW_VAR)) {
			dump("definition -> var_definition");
			return parseVarDefinition();
		} else if (si.getNext().equals(KW_FUN)) {
			dump("definition -> fun_definition");
			si.skip();
			return null;
		} else {
			Report.error(si.getSymbol().position, "Pricakoval typ, var ali fun");
			return null;
		}
	}

	Defs parseDefinitions2() {
		if (si.getNext().equals(OP_SEMICOLON)) {
			dump("definitions2 -> ; definitions");
			si.skip();
			return parseDefinitions();
		} else {
			dump("definitions2 -> e");
			return null;
		}
	}

	TypeDef parseTypeDefinition() {
		dump("type_definition -> typ identifier : type");
		Location startPosition = si.getSymbol().position.start;
		si.skip();
		if (si.getNext().equals(IDENTIFIER)) {
			String name = si.getSymbol().lexeme;
			si.skip();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				var type = parseType();
				return new TypeDef(new Position(startPosition, type.position.end), name, type);
			} else {
				Report.error(si.getSymbol().position, "Pricakoval :");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
			return null;
		}
	}

	Type parseType() {
		Symbol s = si.getSymbol();
		if (si.getNext().equals(IDENTIFIER)) {
			dump("type -> identifier");
			si.skip();
			return new TypeName(s.position, s.lexeme);
		} else if (si.getNext().equals(AT_LOGICAL)) {
			dump("type -> logical");
			si.skip();
			return Atom.LOG(s.position);
		} else if (si.getNext().equals(AT_INTEGER)) {
			dump("type -> integer");
			si.skip();
			return Atom.INT(s.position);
		} else if (si.getNext().equals(AT_STRING)) {
			dump("type -> string");
			si.skip();
			return Atom.STR(s.position);
		} else if (si.getNext().equals(KW_ARR)) {
			dump("type -> arr [ int_const ] type");
			return parseArray();
		} else {
			Report.error(si.getSymbol().position, "pricakoval identifier, logical, integer, string ali arr");
			return null;
		}
	}

	Array parseArray() {
		Location start = si.getSymbol().position.start;
		si.skip();
		if (si.getNext().equals(OP_LBRACKET)) {
			si.skip();
			if (si.getNext().equals(C_INTEGER)) {
				int size = Integer.parseInt(si.getSymbol().lexeme);
				si.skip();
				if (si.getNext().equals(OP_RBRACKET)) {
					si.skip();
					var type = parseType();
					return new Array(new Position(start, type.position.end), size, type);
				} else {
					Report.error(si.getSymbol().position, "pricakoval ]");
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "pricakoval int_const");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "pricakoval [");
			return null;
		}
	}

	VarDef parseVarDefinition() {
		dump("variable_definition -> var identifier : type");
		Location startPosition = si.getSymbol().position.start;
		si.skip();
		if (si.getNext().equals(IDENTIFIER)) {
			String name = si.getSymbol().lexeme;
			si.skip();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				var type = parseType();
				return new VarDef(new Position(startPosition, type.position.end), name, type);
			} else {
				Report.error(si.getSymbol().position, "Pricakoval :");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
			return null;
		}
	}

	void parseFunDefinition() {
		dump("function_definition -> fun identifier ( parameters ) : type = expression");
		if (si.getNext().equals(IDENTIFIER)) {
			String name = si.getSymbol().lexeme;
			si.skip();
			if (si.getNext().equals(OP_LPARENT)) {
				si.skip();
				parseParameters();
				if (si.getNext().equals(OP_RPARENT)) {
					si.skip();
					if (si.getNext().equals(OP_COLON)) {
						si.skip();
						parseType();
						if (si.getNext().equals(OP_ASSIGN)) {
							si.skip();
							parseExpression();
						} else {
							Report.error(si.getSymbol().position, "Pricakoval =");
						}
					} else {
						Report.error(si.getSymbol().position, "Pricakoval :");
					}
				} else {
					Report.error(si.getSymbol().position, "Pricakoval (");
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval (");
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
		}
	}

	void parseParameters() {
		dump("parameters -> parameter parameters2");
		parseParameter();
		parseParameters2();
	}

	void parseParameter() {
		dump("parameter -> identifier : type");
		if (si.getNext().equals(IDENTIFIER)) {
			si.skip();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				parseType();
			} else {
				Report.error(si.getSymbol().position, "Pricakoval :");
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
		}
	}

	void parseParameters2() {
		if (si.getNext().equals(OP_COMMA)) {
			dump("parameters2 -> , parameter parameters2");
			si.skip();
			parseParameter();
			parseParameters2();
		} else {
			dump("parameters2 -> e");
		}
	}

	void parseExpression() {
		dump("expression -> logical_ior_expression expression2");
		parseLogicalIorExpression();
		parseExpression2();
	}

	void parseExpression2() {
		if (si.getNext().equals(OP_LBRACE)) {
			dump("expression2 -> { WHERE definitions }");
			si.skip();
			if (si.getNext().equals(KW_WHERE)) {
				si.skip();
				parseDefinitions();
				if (si.getNext().equals(OP_RBRACE)) {
					si.skip();
				} else {
					Report.error(si.getSymbol().position, "Pricakoval }");
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval WHERE");
			}
		} else {
			dump("expression2 -> e");
		}
	}

	void parseLogicalIorExpression() {
		dump("logical_ior_expression -> logical_and_expression logical_ior_expression2");
		parseLogicalAndExpression();
		parseLogicalIorExpression2();
	}

	void parseLogicalIorExpression2() {
		if (si.getNext().equals(OP_OR)) {
			dump("logical_ior_expression2 -> | logical_and_expression logical_ior_expression2");
			si.skip();
			parseLogicalAndExpression();
			parseLogicalIorExpression2();
		} else {
			dump("logical_ior_expression2 -> e");
		}
	}

	void parseLogicalAndExpression() {
		dump("logical_and_expression -> compare_expression logical_and_expression2");
		parseCompareExpression();
		parseLogicalAndExpression2();
	}

	void parseLogicalAndExpression2() {
		if (si.getNext().equals(OP_AND)) {
			dump("logical_and_expression2 -> & compare_expression logical_and_expression2");
			si.skip();
			parseCompareExpression();
			parseLogicalAndExpression2();
		} else {
			dump("logical_and_expression2 -> e");
		}
	}

	void parseCompareExpression() {
		dump("compare_expression -> additive_expression compare_expression2");
		parseAdditiveExpression();
		parseCompareExpression2();
	}

	void parseCompareExpression2() {
		if (si.getNext().equals(OP_EQ)) {
			dump("compare_expression2 -> == additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else if (si.getNext().equals(OP_NEQ)) {
			dump("compare_expression2 -> != additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else if (si.getNext().equals(OP_LT)) {
			dump("compare_expression2 -> < additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else if (si.getNext().equals(OP_GT)) {
			dump("compare_expression2 -> > additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else if (si.getNext().equals(OP_LEQ)) {
			dump("compare_expression2 -> <= additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else if (si.getNext().equals(OP_GEQ)) {
			dump("compare_expression2 -> >= additive_expression");
			si.skip();
			parseAdditiveExpression();
		} else {
			dump("compare_expression2 -> e");
		}
	}

	void parseAdditiveExpression() {
		dump("additive_expression -> multiplicative_expression additive_expression2");
		parseMultiplicativeExpression();
		parseAdditiveExpression2();
	}

	void parseAdditiveExpression2() {
		if (si.getNext().equals(OP_ADD)) {
			dump("additive_expression2 -> + multiplicative_expression additive_expression2");
			si.skip();
			parseMultiplicativeExpression();
			parseAdditiveExpression2();
		} else if (si.getNext().equals(OP_SUB)) {
			dump("additive_expression2 -> - multiplicative_expression additive_expression2");
			si.skip();
			parseMultiplicativeExpression();
			parseAdditiveExpression2();
		} else {
			dump("additive_expression2 -> e");
		}
	}

	void parseMultiplicativeExpression() {
		dump("multiplicative_expression -> prefix_expression multiplicative_expression2");
		parsePrefixExpression();
		parseMultiplicativeExpression2();
	}

	void parseMultiplicativeExpression2() {
		if (si.getNext().equals(OP_MUL)) {
			dump("multiplicative_expression2 -> * prefix_expression multiplicative_expression2");
			si.skip();
			parsePrefixExpression();
			parseMultiplicativeExpression2();
		} else if (si.getNext().equals(OP_DIV)) {
			dump("multiplicative_expression2 -> / prefix_expression multiplicative_expression2");
			si.skip();
			parsePrefixExpression();
			parseMultiplicativeExpression2();
		} else if (si.getNext().equals(OP_MOD)) {
			dump("multiplicative_expression2 -> % prefix_expression multiplicative_expression2");
			si.skip();
			parsePrefixExpression();
			parseMultiplicativeExpression2();
		} else {
			dump("multiplicative_expression2 -> e");
		}
	}

	void parsePrefixExpression() {
		if (si.getNext().equals(OP_ADD)) {
			dump("prefix_expression -> + prefix_expression");
			si.skip();
			parsePrefixExpression();
		} else if (si.getNext().equals(OP_SUB)) {
			dump("prefix_expression -> - prefix_expression");
			si.skip();
			parsePrefixExpression();
		} else if (si.getNext().equals(OP_NOT)) {
			dump("prefix_expression -> ! prefix_expression");
			si.skip();
			parsePrefixExpression();
		} else {
			dump("prefix_expression -> postfix_expression");
			parsePostfixExpression();
		}
	}

	void parsePostfixExpression() {
		dump("postfix_expression -> atom_expression postfix_expression2");
		parseAtomExpression();
		parsePostfixExpression2();
	}

	void parsePostfixExpression2() {
		if (si.getNext().equals(OP_LBRACKET)) {
			dump("postfix_expression2 -> [ expression ] postfix_expression2");
			si.skip();
			parseExpression();
			if (si.getNext().equals(OP_RBRACKET)) {
				si.skip();
				parsePostfixExpression2();
			} else {
				Report.error(si.getSymbol().position, "Expected ]");
			}
		} else {
			dump("postfix_expression2 -> e");
		}
	}

	void parseAtomExpression() {
		if (si.getNext().equals(C_INTEGER)) {
			dump("atom_expression -> int_constant");
			si.skip();
		} else if (si.getNext().equals(C_LOGICAL)) {
			dump("atom_expression -> logical_constant");
			si.skip();
		} else if (si.getNext().equals(C_STRING)) {
			dump("atom_expression -> string_constant");
			si.skip();
		} else if (si.getNext().equals(IDENTIFIER)) {
			dump("atom_expression -> identifier atom_expression2");
			si.skip();
			parseAtomExpression2();
		} else if (si.getNext().equals(OP_LPARENT)) {
			dump("atom_expression -> ( expression )");
			si.skip();
			parseExpression();
			if (si.getNext().equals(OP_RPARENT)) {
				si.skip();
			} else {
				Report.error("Pricakoval ) dobil " + si.getSymbol());
			}
		} else if (si.getNext().equals(OP_LBRACE)) {
			dump("atom_expression -> { atom_expression4");
			si.skip();
			parseAtomExpression4();
		} else {
			Report.error("Pricakoval int_constant, logical_constant, string_constant, identifier ali ( dobil "
					+ si.getSymbol());

		}
	}

	void parseAtomExpression2() {
		if (si.getNext().equals(OP_LPARENT)) {
			dump("atom_expression2 -> ( expressions )");
			si.skip();
			parseExpressions();
			if (si.getNext().equals(OP_RPARENT)) {
				si.skip();
			} else {
				Report.error(si.getSymbol().position, "Pricakoval ) dobil " + si.getSymbol());
			}
		} else {
			dump("atom_expression2 -> e");
		}
	}

	void parseAtomExpression3() {
		if (si.getNext().equals(OP_RBRACE)) {
			dump("atom_expression3 -> }");
			si.skip();
		} else if (si.getNext().equals(KW_ELSE)) {
			dump("atom_expression3 -> else expression }");
			si.skip();
			parseExpression();
			if (si.getNext().equals(OP_RBRACE)) {
				si.skip();
			} else {
				Report.error("Pricakoval } dobil " + si.getSymbol());
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval } ali else dobil " + si.getSymbol());
		}
	}

	void parseAtomExpression4() {
		if (si.getNext().equals(KW_IF)) {
			dump("atom_expression4 -> if expression then expression atom_expression3");
			si.skip();
			parseExpression();
			if (si.getNext().equals(KW_THEN)) {
				si.skip();
				parseExpression();
				parseAtomExpression3();
			} else {
				Report.error(si.getSymbol().position, "Pricakoval then dobil " + si.getSymbol());
			}
		} else if (si.getNext().equals(KW_WHILE)) {
			dump("atom_expression4 -> while expression : expression }");
			si.skip();
			parseExpression();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				parseExpression();
				if (si.getNext().equals(OP_RBRACE)) {
					si.skip();
				} else {
					Report.error(si.getSymbol().position, "Pricakoval } dobil " + si.getSymbol());
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval : dobil " + si.getSymbol());
			}
		} else if (si.getNext().equals(KW_FOR)) {
			dump("atom_expression4 -> for identifier = expression , expression , expression : expression } .");
			si.skip();
			if (si.getNext().equals(IDENTIFIER)) {
				si.skip();
				if (si.getNext().equals(OP_ASSIGN)) {
					si.skip();
					parseExpression();
					if (si.getNext().equals(OP_COMMA)) {
						si.skip();
						parseExpression();
						if (si.getNext().equals(OP_COMMA)) {
							si.skip();
							parseExpression();
							if (si.getNext().equals(OP_COLON)) {
								si.skip();
								parseExpression();
								if (si.getNext().equals(OP_RBRACE)) {
									si.skip();
								} else {
									Report.error(si.getSymbol().position, "Pricakoval } dobil " + si.getSymbol());
								}
							} else {
								Report.error(si.getSymbol().position, "Pricakoval : dobil " + si.getSymbol());
							}
						} else {
							Report.error(si.getSymbol().position, "Pricakoval , dobil " + si.getSymbol());
						}
					} else {
						Report.error(si.getSymbol().position, "Pricakoval , dobil " + si.getSymbol());
					}
				} else {
					Report.error(si.getSymbol().position, "Pricakoval = dobil " + si.getSymbol());
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval identifier dobil " + si.getSymbol());
			}
		} else {
			dump("atom_expression4 -> expression = expression }");
			parseExpression();
			if (si.getNext().equals(OP_ASSIGN)) {
				si.skip();
				parseExpression();
				if (si.getNext().equals(OP_RBRACE)) {
					si.skip();
				} else {
					Report.error(si.getSymbol().position, "Pricakoval } dobil " + si.getSymbol());
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval = dobil " + si.getSymbol());
			}
		}
	}

	void parseExpressions() {
		dump("expressions -> expression expressions2");
		parseExpression();
		parseExpressions2();
	}

	void parseExpressions2() {
		if (si.getNext().equals(OP_COMMA)) {
			dump("expressions2 -> , expression expressions2");
			si.skip();
			parseExpression();
			parseExpressions2();
		} else {
			dump("expressions2 -> e");
		}
	}

	/**
	 * Izpiše produkcijo na izhodni tok.
	 */
	private void dump(String production) {
		if (productionsOutputStream.isPresent()) {
			productionsOutputStream.get().println(production);
		}
	}
}
