// Generated from /home/amadej/PINSCompiler/src/pinsParser.g4 by ANTLR 4.13.1
package compiler.common.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pinsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pinsParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pinsParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pinsParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#definitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinitions(pinsParser.DefinitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#definitions2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinitions2(pinsParser.Definitions2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(pinsParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#type_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_definition(pinsParser.Type_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#variable_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_definition(pinsParser.Variable_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(pinsParser.Function_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#function_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_declaration(pinsParser.Function_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(pinsParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(pinsParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#parameters2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters2(pinsParser.Parameters2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(pinsParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(pinsParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression2(pinsParser.Expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#logical_ior_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_ior_expression(pinsParser.Logical_ior_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#logical_ior_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_ior_expression2(pinsParser.Logical_ior_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(pinsParser.Logical_and_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#logical_and_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression2(pinsParser.Logical_and_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#compare_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare_expression(pinsParser.Compare_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#compare_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare_expression2(pinsParser.Compare_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(pinsParser.Additive_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#additive_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression2(pinsParser.Additive_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(pinsParser.Multiplicative_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#multiplicative_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression2(pinsParser.Multiplicative_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#prefix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix_expression(pinsParser.Prefix_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expression(pinsParser.Postfix_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#postfix_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expression2(pinsParser.Postfix_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#atom_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expression(pinsParser.Atom_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#atom_expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expression2(pinsParser.Atom_expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#atom_expression3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expression3(pinsParser.Atom_expression3Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#atom_expression4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expression4(pinsParser.Atom_expression4Context ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(pinsParser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link pinsParser#expressions2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions2(pinsParser.Expressions2Context ctx);
}