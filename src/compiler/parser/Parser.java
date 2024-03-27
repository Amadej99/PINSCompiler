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
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.Binary;
import compiler.parser.ast.expr.Block;
import compiler.parser.ast.expr.Call;
import compiler.parser.ast.expr.Expr;
import compiler.parser.ast.expr.For;
import compiler.parser.ast.expr.IfThenElse;
import compiler.parser.ast.expr.Literal;
import compiler.parser.ast.expr.Name;
import compiler.parser.ast.expr.Unary;
import compiler.parser.ast.expr.Where;
import compiler.parser.ast.expr.While;
import compiler.parser.ast.expr.Binary.Operator;
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
		var defs_start = new Defs(null, new ArrayList<Def>());
		var defs = parseDefinitions(defs_start);
		var defs2 = new Defs(new Position(defs.definitions.get(0).position.start,
				defs.definitions.get(defs.definitions.size() - 1).position.end), defs.definitions);
		if (si.getNext().equals(EOF)) {
			return defs2;
		} else {
			Report.error(si.getSymbol().position, "Pricakoval EOF");
			return null;
		}
	}

	Defs parseDefinitions(Defs defs) {
		dump("definitions -> definition definitions2");
		var def = parseDefinition(defs);
		defs.definitions.add(def);
		return parseDefinitions2(defs);
	}

	Def parseDefinition(Defs defs) {
		if (si.getNext().equals(KW_TYP)) {
			dump("definition -> type_definition");
			return parseTypeDefinition();
		} else if (si.getNext().equals(KW_VAR)) {
			dump("definition -> var_definition");
			return parseVarDefinition();
		} else if (si.getNext().equals(KW_FUN)) {
			dump("definition -> fun_definition");
			return parseFunDefinition();
		} else if (si.getNext().equals(KW_DECLARE)) {
			dump("definition -> fun_declaration");
			return parseFunDeclareDefinition();
		} else {
			Report.error(si.getSymbol().position, "Pricakoval typ, var ali fun");
			return null;
		}
	}

	Defs parseDefinitions2(Defs defs) {
		if (si.getNext().equals(OP_SEMICOLON)) {
			dump("definitions2 -> ; definitions");
			si.skip();
			return parseDefinitions(defs);
		} else {
			dump("definitions2 -> e");
			return defs;
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

	FunDef parseFunDeclareDefinition() {
		dump("function_declaration -> fun identifier ( parameters ) : type");
		Symbol start = si.getSymbol();
		si.skip();
		if (si.getNext().equals(IDENTIFIER)) {
			String name = si.getSymbol().lexeme;
			si.skip();
			if (si.getNext().equals(OP_LPARENT)) {
				si.skip();
				var empty_list = new ArrayList<Parameter>();
				var params = parseParameters(empty_list);
				if (si.getNext().equals(OP_RPARENT)) {
					si.skip();
					if (si.getNext().equals(OP_COLON)) {
						si.skip();
						var type = parseType();
						return new FunDef(new Position(start.position.start, type.position.end), name, params,
								type, null);
					} else {
						Report.error(si.getSymbol().position, "Pricakoval :");
						return null;
					}
				} else {
					Report.error(si.getSymbol().position, "Pricakoval (");
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval (");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
			return null;
		}
	}

	FunDef parseFunDefinition() {
		dump("function_definition -> fun identifier ( parameters ) : type = expression");
		Symbol start = si.getSymbol();
		si.skip();
		if (si.getNext().equals(IDENTIFIER)) {
			String name = si.getSymbol().lexeme;
			si.skip();
			if (si.getNext().equals(OP_LPARENT)) {
				si.skip();
				var empty_list = new ArrayList<Parameter>();
				var params = parseParameters(empty_list);
				if (si.getNext().equals(OP_RPARENT)) {
					si.skip();
					if (si.getNext().equals(OP_COLON)) {
						si.skip();
						var type = parseType();
						if (si.getNext().equals(OP_ASSIGN)) {
							si.skip();
							var expr = parseExpression();
							return new FunDef(new Position(start.position.start, expr.position.end), name, params,
									type, expr);
						} else {
							Report.error(si.getSymbol().position, "Pricakoval =");
							return null;
						}
					} else {
						Report.error(si.getSymbol().position, "Pricakoval :");
						return null;
					}
				} else {
					Report.error(si.getSymbol().position, "Pricakoval (");
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval (");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
			return null;
		}
	}

	List<Parameter> parseParameters(List<Parameter> params) {
		dump("parameters -> parameter parameters2");
		var param = parseParameter(params);
		params.add(param);
		return parseParameters2(params);
	}

	Parameter parseParameter(List<Parameter> params) {
		dump("parameter -> identifier : type");
		if (si.getNext().equals(IDENTIFIER)) {
			Symbol s = si.getSymbol();
			si.skip();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				var type = parseType();
				return new Parameter(new Position(s.position.start, type.position.end), s.lexeme, type);
			} else {
				Report.error(si.getSymbol().position, "Pricakoval :");
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval identifier");
			return null;
		}
	}

	List<Parameter> parseParameters2(List<Parameter> params) {
		if (si.getNext().equals(OP_COMMA)) {
			dump("parameters2 -> , parameters");
			si.skip();
			return parseParameters(params);
		} else {
			dump("parameters2 -> e");
			return params;
		}
	}

	Expr parseExpression() {
		dump("expression -> logical_ior_expression expression2");
		var left = parseLogicalIorExpression();
		return parseExpression2(left);
	}

	Expr parseExpression2(Expr left) {
		if (si.getNext().equals(OP_LBRACE)) {
			dump("expression2 -> { WHERE definitions }");
			si.skip();
			if (si.getNext().equals(KW_WHERE)) {
				si.skip();
				var defs_start = new Defs(null, new ArrayList<Def>());
				Defs defs = parseDefinitions(defs_start);
				Defs defs2 = new Defs(new Position(defs.definitions.get(0).position.start,
						defs.definitions.get(defs.definitions.size() - 1).position.end), defs.definitions);
				if (si.getNext().equals(OP_RBRACE)) {
					Symbol end = si.getSymbol();
					si.skip();
					return new Where(new Position(left.position.start, end.position.end), left, defs2);
				} else {
					Report.error(si.getSymbol().position, "Pricakoval }");
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval WHERE");
				return null;
			}
		} else {
			dump("expression2 -> e");
			return left;
		}
	}

	Expr parseLogicalIorExpression() {
		dump("logical_ior_expression -> logical_and_expression logical_ior_expression2");
		var left = parseLogicalAndExpression();
		return parseLogicalIorExpression2(left);
	}

	Expr parseLogicalIorExpression2(Expr left) {
		if (si.getNext().equals(OP_OR)) {
			dump("logical_ior_expression2 -> | logical_and_expression logical_ior_expression2");
			si.skip();
			var right = parseLogicalAndExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.OR, right);
			return parseLogicalIorExpression2(bin);
		} else {
			dump("logical_ior_expression2 -> e");
			return left;
		}
	}

	Expr parseLogicalAndExpression() {
		dump("logical_and_expression -> compare_expression logical_and_expression2");
		var left = parseCompareExpression();
		return parseLogicalAndExpression2(left);
	}

	Expr parseLogicalAndExpression2(Expr left) {
		if (si.getNext().equals(OP_AND)) {
			dump("logical_and_expression2 -> & compare_expression logical_and_expression2");
			si.skip();
			var right = parseCompareExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.AND, right);
			return parseLogicalAndExpression2(bin);
		} else {
			dump("logical_and_expression2 -> e");
			return left;
		}
	}

	Expr parseCompareExpression() {
		dump("compare_expression -> additive_expression compare_expression2");
		var left = parseAdditiveExpression();
		return parseCompareExpression2(left);
	}

	Expr parseCompareExpression2(Expr left) {
		if (si.getNext().equals(OP_EQ)) {
			dump("compare_expression2 -> == additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.EQ, right);
		} else if (si.getNext().equals(OP_NEQ)) {
			dump("compare_expression2 -> != additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.NEQ, right);
		} else if (si.getNext().equals(OP_LT)) {
			dump("compare_expression2 -> < additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.LT, right);
		} else if (si.getNext().equals(OP_GT)) {
			dump("compare_expression2 -> > additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.GT, right);
		} else if (si.getNext().equals(OP_LEQ)) {
			dump("compare_expression2 -> <= additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.LEQ, right);
		} else if (si.getNext().equals(OP_GEQ)) {
			dump("compare_expression2 -> >= additive_expression");
			si.skip();
			var right = parseAdditiveExpression();
			return new Binary(new Position(left.position.start, right.position.end), left, Operator.GEQ, right);
		} else {
			dump("compare_expression2 -> e");
			return left;
		}
	}

	Expr parseAdditiveExpression() {
		dump("additive_expression -> multiplicative_expression additive_expression2");
		var left = parseMultiplicativeExpression();
		return parseAdditiveExpression2(left);
	}

	Expr parseAdditiveExpression2(Expr left) {
		if (si.getNext().equals(OP_ADD)) {
			dump("additive_expression2 -> + multiplicative_expression additive_expression2");
			si.skip();
			var right = parseMultiplicativeExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.ADD, right);
			return parseAdditiveExpression2(bin);
		} else if (si.getNext().equals(OP_SUB)) {
			dump("additive_expression2 -> - multiplicative_expression additive_expression2");
			si.skip();
			var right = parseMultiplicativeExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.SUB, right);
			return parseAdditiveExpression2(bin);
		} else {
			dump("additive_expression2 -> e");
			return left;
		}
	}

	Expr parseMultiplicativeExpression() {
		dump("multiplicative_expression -> prefix_expression multiplicative_expression2");
		var left = parsePrefixExpression();
		return parseMultiplicativeExpression2(left);
	}

	Expr parseMultiplicativeExpression2(Expr left) {
		if (si.getNext().equals(OP_MUL)) {
			dump("multiplicative_expression2 -> * prefix_expression multiplicative_expression2");
			si.skip();
			var right = parsePrefixExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.MUL, right);
			return parseMultiplicativeExpression2(bin);
		} else if (si.getNext().equals(OP_DIV)) {
			dump("multiplicative_expression2 -> / prefix_expression multiplicative_expression2");
			si.skip();
			var right = parsePrefixExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.DIV, right);
			return parseMultiplicativeExpression2(bin);
		} else if (si.getNext().equals(OP_MOD)) {
			dump("multiplicative_expression2 -> % prefix_expression multiplicative_expression2");
			si.skip();
			var right = parsePrefixExpression();
			var bin = new Binary(new Position(left.position.start, right.position.end), left, Operator.MOD, right);
			return parseMultiplicativeExpression2(bin);
		} else {
			dump("multiplicative_expression2 -> e");
			return left;
		}
	}

	Expr parsePrefixExpression() {
		Symbol s = si.getSymbol();
		if (si.getNext().equals(OP_ADD)) {
			dump("prefix_expression -> + prefix_expression");
			si.skip();
			var izraz = parsePrefixExpression();
			return new Unary(new Position(s.position.start, izraz.position.end), izraz, Unary.Operator.ADD);
		} else if (si.getNext().equals(OP_SUB)) {
			dump("prefix_expression -> - prefix_expression");
			si.skip();
			var izraz = parsePrefixExpression();
			return new Unary(new Position(s.position.start, izraz.position.end), izraz, Unary.Operator.SUB);
		} else if (si.getNext().equals(OP_NOT)) {
			dump("prefix_expression -> ! prefix_expression");
			si.skip();
			var izraz = parsePrefixExpression();
			return new Unary(new Position(s.position.start, izraz.position.end), izraz, Unary.Operator.NOT);
		} else {
			dump("prefix_expression -> postfix_expression");
			return parsePostfixExpression();
		}
	}

	Expr parsePostfixExpression() {
		dump("postfix_expression -> atom_expression postfix_expression2");
		var left = parseAtomExpression();
		return parsePostfixExpression2(left);
	}

	Expr parsePostfixExpression2(Expr left) {
		if (si.getNext().equals(OP_LBRACKET)) {
			dump("postfix_expression2 -> [ expression ] postfix_expression2");
			si.skip();
			var right = parseExpression();
			if (si.getNext().equals(OP_RBRACKET)) {
				Symbol end = si.getSymbol();
				si.skip();
				var bin = new Binary(new Position(left.position.start, end.position.end), left, Operator.ARR,
						right);
				return parsePostfixExpression2(bin);
			} else {
				Report.error(si.getSymbol().position, "Expected ]");
				return null;
			}
		} else {
			dump("postfix_expression2 -> e");
			return left;
		}
	}

	Expr parseAtomExpression() {
		Symbol s = si.getSymbol();
		if (si.getNext().equals(C_INTEGER)) {
			dump("atom_expression -> int_constant");
			si.skip();
			return new Literal(s.position, s.lexeme, Atom.Type.INT);
		} else if (si.getNext().equals(C_LOGICAL)) {
			dump("atom_expression -> logical_constant");
			si.skip();
			return new Literal(s.position, s.lexeme, Atom.Type.LOG);
		} else if (si.getNext().equals(C_STRING)) {
			dump("atom_expression -> string_constant");
			si.skip();
			return new Literal(s.position, s.lexeme, Atom.Type.STR);
		} else if (si.getNext().equals(IDENTIFIER)) {
			dump("atom_expression -> identifier atom_expression2");
			si.skip();
			var left = new Name(s.position, s.lexeme);
			return parseAtomExpression2(left);
		} else if (si.getNext().equals(OP_LPARENT)) {
			dump("atom_expression -> ( expression )");
			si.skip();
			var exprs = parseExpressions(new ArrayList<Expr>());
			if (si.getNext().equals(OP_RPARENT)) {
				Symbol end = si.getSymbol();
				si.skip();
				return new Block(new Position(s.position.start, end.position.end), exprs);
			} else {
				Report.error("Pricakoval ) dobil " + si.getSymbol());
				return null;
			}
		} else if (si.getNext().equals(OP_LBRACE)) {
			dump("atom_expression -> { atom_expression4");
			return parseAtomExpression4();
		} else {
			Report.error("Pricakoval int_constant, logical_constant, string_constant, identifier ali ( dobil "
					+ si.getSymbol());
			return null;

		}
	}

	Expr parseAtomExpression2(Name left) {
		if (si.getNext().equals(OP_LPARENT)) {
			dump("atom_expression2 -> ( expressions )");
			si.skip();
			var empty_list = new ArrayList<Expr>();
			var args = parseExpressions(empty_list);
			if (si.getNext().equals(OP_RPARENT)) {
				Symbol end = si.getSymbol();
				si.skip();
				return new Call(new Position(left.position.start, end.position.end), args,
						left.name);
			} else {
				Report.error(si.getSymbol().position, "Pricakoval ) dobil " +
						si.getSymbol());
				return null;
			}
		} else {
			dump("atom_expression2 -> e");
			return left;
		}
	}

	Expr parseAtomExpression3() {
		if (si.getNext().equals(OP_RBRACE)) {
			dump("atom_expression3 -> }");
			return null;
		} else if (si.getNext().equals(KW_ELSE)) {
			dump("atom_expression3 -> else expression }");
			si.skip();
			var expr = parseExpression();
			if (si.getNext().equals(OP_RBRACE)) {
				return expr;
			} else {
				Report.error("Pricakoval } dobil " + si.getSymbol());
				return null;
			}
		} else {
			Report.error(si.getSymbol().position, "Pricakoval } ali else dobil " + si.getSymbol());
			return null;
		}
	}

	Expr parseAtomExpression4() {
		Symbol s = si.getSymbol();
		si.skip();
		if (si.getNext().equals(KW_IF)) {
			dump("atom_expression4 -> if expression then expression atom_expression3");
			si.skip();
			var expr = parseExpression();
			if (si.getNext().equals(KW_THEN)) {
				si.skip();
				var then = parseExpression();
				var else_expr = parseAtomExpression3();
				Symbol end = si.getSymbol();
				si.skip();
				if (else_expr == null)
					return new IfThenElse(
							new Position(s.position.start, end.position.end),
							expr, then);
				else
					return new IfThenElse(new Position(s.position.start, end.position.end), expr, then,
							else_expr);
			} else {
				Report.error(si.getSymbol().position, "Pricakoval then dobil " +
						si.getSymbol());
				return null;
			}
		} else if (si.getNext().equals(KW_WHILE)) {
			dump("atom_expression4 -> while expression : expression }");
			si.skip();
			var expr = parseExpression();
			if (si.getNext().equals(OP_COLON)) {
				si.skip();
				var expr2 = parseExpression();
				if (si.getNext().equals(OP_RBRACE)) {
					Symbol end = si.getSymbol();
					si.skip();
					return new While(
							new Position(s.position.start, end.position.end),
							expr, expr2);
				} else {
					Report.error(si.getSymbol().position, "Pricakoval } dobil " +
							si.getSymbol());
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval : dobil " +
						si.getSymbol());
				return null;
			}
		} else if (si.getNext().equals(KW_FOR)) {
			dump("atom_expression4 -> for identifier = expression , expression , expression : expression } .");
			si.skip();
			if (si.getNext().equals(IDENTIFIER)) {
				var identifier = new Name(si.getSymbol().position, si.getSymbol().lexeme);
				si.skip();
				if (si.getNext().equals(OP_ASSIGN)) {
					si.skip();
					var expr = parseExpression();
					if (si.getNext().equals(OP_COMMA)) {
						si.skip();
						var expr2 = parseExpression();
						if (si.getNext().equals(OP_COMMA)) {
							si.skip();
							var expr3 = parseExpression();
							if (si.getNext().equals(OP_COLON)) {
								si.skip();
								var expr4 = parseExpression();
								if (si.getNext().equals(OP_RBRACE)) {
									Symbol end = si.getSymbol();
									si.skip();
									return new For(
											new Position(s.position.start, end.position.end),
											identifier, expr, expr2, expr3, expr4);
								} else {
									Report.error(si.getSymbol().position, "Pricakoval } dobil " +
											si.getSymbol());
									return null;
								}
							} else {
								Report.error(si.getSymbol().position, "Pricakoval : dobil " +
										si.getSymbol());
								return null;
							}
						} else {
							Report.error(si.getSymbol().position, "Pricakoval , dobil " +
									si.getSymbol());
							return null;
						}
					} else {
						Report.error(si.getSymbol().position, "Pricakoval , dobil " +
								si.getSymbol());
						return null;
					}
				} else {
					Report.error(si.getSymbol().position, "Pricakoval = dobil " +
							si.getSymbol());
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval identifier dobil " +
						si.getSymbol());
				return null;
			}
		} else {
			dump("atom_expression4 -> expression = expression }");
			var expr = parseExpression();
			if (si.getNext().equals(OP_ASSIGN)) {
				si.skip();
				var expr2 = parseExpression();
				if (si.getNext().equals(OP_RBRACE)) {
					Symbol end = si.getSymbol();
					si.skip();
					return new Binary(
							new Position(s.position.start, end.position.end),
							expr, Operator.ASSIGN, expr2);
				} else {
					Report.error(si.getSymbol().position, "Pricakoval } dobil " +
							si.getSymbol());
					return null;
				}
			} else {
				Report.error(si.getSymbol().position, "Pricakoval = dobil " +
						si.getSymbol());
				return null;
			}
		}
	}

	List<Expr> parseExpressions(List<Expr> args) {
		dump("expressions -> expression expressions2");
		var arg = parseExpression();
		args.add(arg);
		return parseExpressions2(args);
	}

	List<Expr> parseExpressions2(List<Expr> args) {
		if (si.getNext().equals(OP_COMMA)) {
			dump("expressions2 -> , expression expressions2");
			si.skip();
			return parseExpressions(args);
		} else {
			dump("expressions2 -> e");
			return args;
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
