package compiler.parser;

import common.Report;
import compiler.common.antlr.pinsBaseVisitor;
import compiler.common.antlr.pinsParser.*;
import compiler.lexer.Position;
import compiler.parser.ast.Ast;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameters;
import compiler.parser.ast.def.FunDef.Parameters.Parameter;
import compiler.parser.ast.def.FunDef.Parameters.VarArg;
import compiler.parser.ast.expr.*;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.Type;
import compiler.parser.ast.type.TypeName;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Optional;

public class pinsAstVisitor extends pinsBaseVisitor<Ast> {

    @Override
    public Ast visitProgram(ProgramContext ctx) {
        return visitDefinitions(ctx.definitions());
    }

    @Override
    public Defs visitDefinitions(DefinitionsContext ctx) {
        var definitions = new ArrayList<Def>();
        definitions.add(this.visitDefinition(ctx.definition()));
        var definitions2 = this.visitDefinitions2(ctx.definitions2());
        if (!definitions2.definitions.isEmpty())
            definitions.addAll(definitions2.definitions);

        return new Defs(getContextPosition(ctx), definitions);
    }

    @Override
    public Defs visitDefinitions2(Definitions2Context ctx) {
        if (ctx.definitions() != null)
            return this.visitDefinitions(ctx.definitions());

        return new Defs(getContextPosition(ctx), new ArrayList<>());
    }

    @Override
    public Def visitDefinition(DefinitionContext ctx) {
        if (ctx.variable_definition() != null)
            return this.visitVariable_definition(ctx.variable_definition());
        if (ctx.type_definition() != null)
            return this.visitType_definition(ctx.type_definition());
        if (ctx.function_declaration() != null)
            return this.visitFunction_declaration(ctx.function_declaration());
        if (ctx.function_definition() != null)
            return this.visitFunction_definition(ctx.function_definition());
        return null;
    }

    @Override
    public FunDef visitFunction_declaration(Function_declarationContext ctx) {
        var name = ctx.identifier().getText();
        var parameters = this.visitParameters(ctx.parameters());
        var type = this.visitType(ctx.type());

        var isVarArg = parameters.definitions.removeIf(parameter -> parameter instanceof VarArg);

        return new FunDef(getContextPosition(ctx), name,
                parameters.definitions.isEmpty() ? Optional.empty() : Optional.of(parameters), type, Optional.empty(),
                isVarArg);
    }

    @Override
    public FunDef visitFunction_definition(Function_definitionContext ctx) {
        var name = ctx.identifier().getText();
        var parameters = this.visitParameters(ctx.parameters());
        var type = this.visitType(ctx.type());
        var expr = this.visitExpression(ctx.expression());

        var isVarArg = parameters.definitions.removeIf(parameter -> parameter instanceof VarArg);
        if (isVarArg)
            Report.error(getContextPosition(ctx),
                    "Variabilno število parametrov se lahko uporablja samo pri deklaracijah zunanjih funkcij!");

        return new FunDef(getContextPosition(ctx), name,
                parameters.definitions.isEmpty() ? Optional.empty() : Optional.of(parameters), type, Optional.of(expr),
                false);
    }

    @Override
    public Parameters visitParameters(ParametersContext ctx) {
        var parameters = new ArrayList<Def>();
        var firstParameter = this.visitParameter(ctx.parameter());

        if (firstParameter == null)
            return new Parameters(getContextPosition(ctx), new ArrayList<>());

        parameters.add(firstParameter);
        var parameters2 = this.visitParameters2(ctx.parameters2());
        if (!parameters2.definitions.isEmpty())
            parameters.addAll(parameters2.definitions);
        return new Parameters(getContextPosition(ctx), parameters);
    }

    @Override
    public Parameters visitParameters2(Parameters2Context ctx) {
        if (ctx.parameters() != null)
            return this.visitParameters(ctx.parameters());
        return new Parameters(getContextPosition(ctx), new ArrayList<>());
    }

    @Override
    public Parameter visitParameter(ParameterContext ctx) {
        if (ctx.OP_VARARG() != null)
            return new VarArg(getContextPosition(ctx), "varArg");

        if (ctx.identifier() == null)
            return null;

        var name = ctx.identifier().getText();
        var type = this.visitType(ctx.type());
        return new Parameter(getContextPosition(ctx), name, type);
    }

    @Override
    public VarDef visitVariable_definition(Variable_definitionContext ctx) {
        var name = ctx.children.get(1).getText();
        var type = this.visitType(ctx.type());
        return new VarDef(getContextPosition(ctx), name, type);
    }

    @Override
    public TypeDef visitType_definition(Type_definitionContext ctx) {
        var name = ctx.identifier().getText();
        var type = this.visitType(ctx.type());
        return new TypeDef(getContextPosition(ctx), name, type);
    }

    @Override
    public Type visitType(TypeContext ctx) {
        if (ctx.identifier() != null)
            return new TypeName(getContextPosition(ctx), ctx.identifier().getText());
        if (ctx.integer() != null)
            return Atom.INT(getContextPosition(ctx));
        if (ctx.logical() != null)
            return Atom.LOG(getContextPosition(ctx));
        if (ctx.string() != null)
            return Atom.STR(getContextPosition(ctx));
        if (ctx.arr() != null) {
            var type = this.visitType(ctx.type());
            var size = Integer.parseInt(ctx.children.get(2).getText());
            return new Array(getContextPosition(ctx), size, type);
        }
        return null;
    }

    @Override
    public Expr visitExpression(ExpressionContext ctx) {
        var left = visitLogical_ior_expression(ctx.logical_ior_expression());
        return left;
    }

    @Override
    public Expr visitExpression2(Expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitLogical_ior_expression(Logical_ior_expressionContext ctx) {
        var left = visitLogical_and_expression(ctx.logical_and_expression());
        return left;
    }

    @Override
    public Expr visitLogical_ior_expression2(Logical_ior_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitLogical_and_expression(Logical_and_expressionContext ctx) {
        var left = visitCompare_expression(ctx.compare_expression());
        return left;
    }

    @Override
    public Expr visitLogical_and_expression2(Logical_and_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitCompare_expression(Compare_expressionContext ctx) {
        var left = visitAdditive_expression(ctx.additive_expression());
        return left;
    }

    @Override
    public Expr visitCompare_expression2(Compare_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitAdditive_expression(Additive_expressionContext ctx) {
        var left = visitMultiplicative_expression(ctx.multiplicative_expression());
        return left;
    }

    @Override
    public Expr visitAdditive_expression2(Additive_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitMultiplicative_expression(Multiplicative_expressionContext ctx) {
        var left = visitPrefix_expression(ctx.prefix_expression());
        return left;
    }

    @Override
    public Expr visitMultiplicative_expression2(Multiplicative_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitPrefix_expression(Prefix_expressionContext ctx) {
        var left = visitPostfix_expression(ctx.postfix_expression());
        return left;
    }

    @Override
    public Expr visitPostfix_expression(Postfix_expressionContext ctx) {
        var left = visitAtom_expression(ctx.atom_expression());
        return left;
    }

    @Override
    public Expr visitPostfix_expression2(Postfix_expression2Context ctx) {
        return null;
    }

    @Override
    public Expr visitAtom_expression(Atom_expressionContext ctx) {
        if (ctx.log_constant() != null)
            return new Literal(getContextPosition(ctx), ctx.log_constant().getText(), Atom.Type.LOG);
        if (ctx.int_constant() != null)
            return new Literal(getContextPosition(ctx), ctx.int_constant().getText(), Atom.Type.INT);
        if (ctx.str_constant() != null)
            return new Literal(getContextPosition(ctx), ctx.str_constant().getText(), Atom.Type.STR);
        if (ctx.identifier() != null) {
            var name = ctx.identifier().getText();
            var expressions = visitAtom_expression2(ctx.atom_expression2());

            if (expressions instanceof Block block) {
                var expressionList = new ArrayList<Expr>(block.expressions);
                return new Call(getContextPosition(ctx),
                        expressionList.isEmpty() ? Optional.empty() : Optional.of(expressionList), name);
            }

            return new Name(getContextPosition(ctx), ctx.identifier().getText());
        }
        if (ctx.expressions() != null)
            return visitExpressions(ctx.expressions());
        if (ctx.atom_expression4() != null)
            return visitAtom_expression4(ctx.atom_expression4());
        return null;
    }

    @Override
    public Expr visitAtom_expression2(Atom_expression2Context ctx) {
        if (ctx.expressions() != null)
            return visitExpressions(ctx.expressions());
        if (ctx.OP_LPARENT() != null && ctx.OP_RPARENT() != null)
            return new Block(getContextPosition(ctx), new ArrayList<>());
        return null;
    }

    @Override
    public Expr visitAtom_expression3(Atom_expression3Context ctx) {
        return null;
    }

    @Override
    public Expr visitAtom_expression4(Atom_expression4Context ctx) {
        return null;
    }

    @Override
    public Block visitExpressions(ExpressionsContext ctx) {
        var expressions = new ArrayList<Expr>();
        expressions.add(visitExpression(ctx.expression()));
        var expressions2 = visitExpressions2(ctx.expressions2());
        if (!expressions2.expressions.isEmpty())
            expressions.addAll(expressions2.expressions);
        return new Block(getContextPosition(ctx), expressions);
    }

    @Override
    public Block visitExpressions2(Expressions2Context ctx) {
        if (ctx.expressions() != null)
            return visitExpressions(ctx.expressions());
        return new Block(getContextPosition(ctx), new ArrayList<>());
    }

    private Position getContextPosition(ParserRuleContext ctx) {
        return new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.stop.getLine(),
                ctx.stop.getCharPositionInLine());
    }
}
