// Generated from /home/amadej/pinscompiler/src/pins.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pinsParser}.
 */
public interface pinsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pinsParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(pinsParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(pinsParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#definitions}.
	 * @param ctx the parse tree
	 */
	void enterDefinitions(pinsParser.DefinitionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#definitions}.
	 * @param ctx the parse tree
	 */
	void exitDefinitions(pinsParser.DefinitionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#definitions2}.
	 * @param ctx the parse tree
	 */
	void enterDefinitions2(pinsParser.Definitions2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#definitions2}.
	 * @param ctx the parse tree
	 */
	void exitDefinitions2(pinsParser.Definitions2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(pinsParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(pinsParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#type_definition}.
	 * @param ctx the parse tree
	 */
	void enterType_definition(pinsParser.Type_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#type_definition}.
	 * @param ctx the parse tree
	 */
	void exitType_definition(pinsParser.Type_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void enterVariable_definition(pinsParser.Variable_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void exitVariable_definition(pinsParser.Variable_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(pinsParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(pinsParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#function_declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunction_declaration(pinsParser.Function_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#function_declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunction_declaration(pinsParser.Function_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(pinsParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(pinsParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(pinsParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(pinsParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#parameters2}.
	 * @param ctx the parse tree
	 */
	void enterParameters2(pinsParser.Parameters2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#parameters2}.
	 * @param ctx the parse tree
	 */
	void exitParameters2(pinsParser.Parameters2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(pinsParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(pinsParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(pinsParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(pinsParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#expression2}.
	 * @param ctx the parse tree
	 */
	void enterExpression2(pinsParser.Expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#expression2}.
	 * @param ctx the parse tree
	 */
	void exitExpression2(pinsParser.Expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#logical_ior_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_ior_expression(pinsParser.Logical_ior_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#logical_ior_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_ior_expression(pinsParser.Logical_ior_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#logical_ior_expression2}.
	 * @param ctx the parse tree
	 */
	void enterLogical_ior_expression2(pinsParser.Logical_ior_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#logical_ior_expression2}.
	 * @param ctx the parse tree
	 */
	void exitLogical_ior_expression2(pinsParser.Logical_ior_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(pinsParser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(pinsParser.Logical_and_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#logical_and_expression2}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression2(pinsParser.Logical_and_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#logical_and_expression2}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression2(pinsParser.Logical_and_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#compare_expression}.
	 * @param ctx the parse tree
	 */
	void enterCompare_expression(pinsParser.Compare_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#compare_expression}.
	 * @param ctx the parse tree
	 */
	void exitCompare_expression(pinsParser.Compare_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#compare_expression2}.
	 * @param ctx the parse tree
	 */
	void enterCompare_expression2(pinsParser.Compare_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#compare_expression2}.
	 * @param ctx the parse tree
	 */
	void exitCompare_expression2(pinsParser.Compare_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression(pinsParser.Additive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression(pinsParser.Additive_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#additive_expression2}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression2(pinsParser.Additive_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#additive_expression2}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression2(pinsParser.Additive_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression(pinsParser.Multiplicative_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression(pinsParser.Multiplicative_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#multiplicative_expression2}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression2(pinsParser.Multiplicative_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#multiplicative_expression2}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression2(pinsParser.Multiplicative_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#prefix_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefix_expression(pinsParser.Prefix_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#prefix_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefix_expression(pinsParser.Prefix_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expression(pinsParser.Postfix_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expression(pinsParser.Postfix_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#postfix_expression2}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expression2(pinsParser.Postfix_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#postfix_expression2}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expression2(pinsParser.Postfix_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#atom_expression}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expression(pinsParser.Atom_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#atom_expression}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expression(pinsParser.Atom_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#atom_expression2}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expression2(pinsParser.Atom_expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#atom_expression2}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expression2(pinsParser.Atom_expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#atom_expression3}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expression3(pinsParser.Atom_expression3Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#atom_expression3}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expression3(pinsParser.Atom_expression3Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#atom_expression4}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expression4(pinsParser.Atom_expression4Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#atom_expression4}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expression4(pinsParser.Atom_expression4Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#expressions}.
	 * @param ctx the parse tree
	 */
	void enterExpressions(pinsParser.ExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#expressions}.
	 * @param ctx the parse tree
	 */
	void exitExpressions(pinsParser.ExpressionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#expressions2}.
	 * @param ctx the parse tree
	 */
	void enterExpressions2(pinsParser.Expressions2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#expressions2}.
	 * @param ctx the parse tree
	 */
	void exitExpressions2(pinsParser.Expressions2Context ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(pinsParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(pinsParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#logical}.
	 * @param ctx the parse tree
	 */
	void enterLogical(pinsParser.LogicalContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#logical}.
	 * @param ctx the parse tree
	 */
	void exitLogical(pinsParser.LogicalContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(pinsParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(pinsParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#int_constant}.
	 * @param ctx the parse tree
	 */
	void enterInt_constant(pinsParser.Int_constantContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#int_constant}.
	 * @param ctx the parse tree
	 */
	void exitInt_constant(pinsParser.Int_constantContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#log_constant}.
	 * @param ctx the parse tree
	 */
	void enterLog_constant(pinsParser.Log_constantContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#log_constant}.
	 * @param ctx the parse tree
	 */
	void exitLog_constant(pinsParser.Log_constantContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#str_constant}.
	 * @param ctx the parse tree
	 */
	void enterStr_constant(pinsParser.Str_constantContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#str_constant}.
	 * @param ctx the parse tree
	 */
	void exitStr_constant(pinsParser.Str_constantContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(pinsParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(pinsParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#fun}.
	 * @param ctx the parse tree
	 */
	void enterFun(pinsParser.FunContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#fun}.
	 * @param ctx the parse tree
	 */
	void exitFun(pinsParser.FunContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#typ}.
	 * @param ctx the parse tree
	 */
	void enterTyp(pinsParser.TypContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#typ}.
	 * @param ctx the parse tree
	 */
	void exitTyp(pinsParser.TypContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(pinsParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(pinsParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#arr}.
	 * @param ctx the parse tree
	 */
	void enterArr(pinsParser.ArrContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#arr}.
	 * @param ctx the parse tree
	 */
	void exitArr(pinsParser.ArrContext ctx);
	/**
	 * Enter a parse tree produced by {@link pinsParser#declare}.
	 * @param ctx the parse tree
	 */
	void enterDeclare(pinsParser.DeclareContext ctx);
	/**
	 * Exit a parse tree produced by {@link pinsParser#declare}.
	 * @param ctx the parse tree
	 */
	void exitDeclare(pinsParser.DeclareContext ctx);
}