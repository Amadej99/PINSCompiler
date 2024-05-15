package compiler.parser;

import common.Report;
import compiler.common.antlr.pinsParserBaseVisitor;
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

public class pinsAstVisitor extends pinsParserBaseVisitor<Ast> {

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
        var name = ctx.IDENTIFIER().getText();
        var parameters = this.visitParameters(ctx.parameters());
        var type = this.visitType(ctx.type());

        var isVarArg = parameters.definitions.removeIf(parameter -> parameter instanceof VarArg);

        return new FunDef(getContextPosition(ctx), name,
                parameters.definitions.isEmpty() ? Optional.empty() : Optional.of(parameters), type, Optional.empty(),
                isVarArg);
    }

    @Override
    public FunDef visitFunction_definition(Function_definitionContext ctx) {
        var name = ctx.IDENTIFIER().getText();
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

        if (ctx.IDENTIFIER() == null)
            return null;

        var name = ctx.IDENTIFIER().getText();
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
        var name = ctx.IDENTIFIER().getText();
        var type = this.visitType(ctx.type());
        return new TypeDef(getContextPosition(ctx), name, type);
    }

    @Override
    public Type visitType(TypeContext ctx) {
        if (ctx.IDENTIFIER() != null)
            return new TypeName(getContextPosition(ctx), ctx.IDENTIFIER().getText());
        if (ctx.AT_INTEGER() != null)
            return Atom.INT(getContextPosition(ctx));
        if (ctx.AT_LOGICAL() != null)
            return Atom.LOG(getContextPosition(ctx));
        if (ctx.AT_STRING() != null)
            return Atom.STR(getContextPosition(ctx));
        if (ctx.ARR() != null) {
            var type = this.visitType(ctx.type());
            var size = Integer.parseInt(ctx.children.get(2).getText());
            return new Array(getContextPosition(ctx), size, type);
        }
        return null;
    }

    @Override
    public Expr visitExpression(ExpressionContext ctx) {
        var expression = visitLogical_ior_expression(ctx.logical_ior_expression());
        var definitions = visitExpression2(ctx.expression2());

        if (!definitions.definitions.isEmpty())
            return new Where(getContextPosition(ctx), expression, definitions);

        return expression;
    }

    @Override
    public Defs visitExpression2(Expression2Context ctx) {
        if (ctx.definitions() != null) {
            return visitDefinitions(ctx.definitions());
        }
        return new Defs(getContextPosition(ctx), new ArrayList<>());
    }

    @Override
    public Expr visitLogical_ior_expression(Logical_ior_expressionContext ctx) {
        var left = visitLogical_and_expression(ctx.logical_and_expression());
        var right = visitLogical_ior_expression2(ctx.logical_ior_expression2());

        if (right != null)
            return new Binary(getContextPosition(ctx), left, Binary.Operator.OR, right);

        return left;
    }

    @Override
    public Expr visitLogical_ior_expression2(Logical_ior_expression2Context ctx) {
        if (ctx.logical_ior_expression() != null)
            return visitLogical_ior_expression(ctx.logical_ior_expression());

        return null;
    }

    @Override
    public Expr visitLogical_and_expression(Logical_and_expressionContext ctx) {
        var left = visitCompare_expression(ctx.compare_expression());
        var right = visitLogical_and_expression2(ctx.logical_and_expression2());

        if (right != null)
            return new Binary(getContextPosition(ctx), left, Binary.Operator.AND, right);

        return left;
    }

    @Override
    public Expr visitLogical_and_expression2(Logical_and_expression2Context ctx) {
        if (ctx.logical_and_expression() != null)
            return visitLogical_and_expression(ctx.logical_and_expression());

        return null;
    }

    @Override
    public Expr visitCompare_expression(Compare_expressionContext ctx) {
        var left = visitAdditive_expression(ctx.additive_expression());
        var rightBinary = visitCompare_expression2(ctx.compare_expression2());

        if (rightBinary != null)
            return new Binary(getContextPosition(ctx), left, rightBinary.operator, rightBinary.right);

        return left;
    }

    @Override
    public Binary visitCompare_expression2(Compare_expression2Context ctx) {
        if (ctx.op != null) {
            var right = visitAdditive_expression(ctx.additive_expression());
            return new Binary(getContextPosition(ctx), visitAdditive_expression(ctx.additive_expression()),
                    Binary.Operator.fromSymbol(ctx.op.getText()), right);
        }

        return null;
    }

    @Override
    public Expr visitAdditive_expression(Additive_expressionContext ctx) {
        var left = visitMultiplicative_expression(ctx.multiplicative_expression());
        var rightBinary = visitAdditive_expression2(ctx.additive_expression2());

        if (rightBinary != null)
            return new Binary(getContextPosition(ctx), left, rightBinary.operator, rightBinary.right);

        return left;
    }

    @Override
    public Binary visitAdditive_expression2(Additive_expression2Context ctx) {
        if (ctx.op != null) {
            var right = visitAdditive_expression(ctx.additive_expression());
            return new Binary(getContextPosition(ctx), visitAdditive_expression(ctx.additive_expression()),
                    Binary.Operator.fromSymbol(ctx.op.getText()), right);
        }

        return null;
    }

    @Override
    public Expr visitMultiplicative_expression(Multiplicative_expressionContext ctx) {
        var left = visitPrefix_expression(ctx.prefix_expression());
        var rightBinary = visitMultiplicative_expression2(ctx.multiplicative_expression2());

        if (rightBinary != null)
            return new Binary(getContextPosition(ctx), left, rightBinary.operator, rightBinary.right);

        return left;
    }

    @Override
    public Binary visitMultiplicative_expression2(Multiplicative_expression2Context ctx) {
        if (ctx.op != null) {
            var right = visitMultiplicative_expression(ctx.multiplicative_expression());
            return new Binary(getContextPosition(ctx), visitMultiplicative_expression(ctx.multiplicative_expression()),
                    Binary.Operator.fromSymbol(ctx.op.getText()), right);
        }

        return null;
    }

    @Override
    public Expr visitPrefix_expression(Prefix_expressionContext ctx) {
        if (ctx.op != null) {
            var expr = visitPrefix_expression(ctx.prefix_expression());
            return new Unary(getContextPosition(ctx), expr, Unary.Operator.fromSymbol(ctx.op.getText()));
        }

        return visitPostfix_expression(ctx.postfix_expression());
    }

    @Override
    public Expr visitPostfix_expression(Postfix_expressionContext ctx) {
        var left = visitAtom_expression(ctx.atom_expression());
        var right = visitPostfix_expression2(ctx.postfix_expression2());

        if (right != null) {
            if (right instanceof Binary rightBinary) {
                var createdBinary = new Binary(getContextPosition(ctx), left, Binary.Operator.ARR, rightBinary.left);
                return new Binary(getContextPosition(ctx), createdBinary, Binary.Operator.ARR, rightBinary.right);
            }
            return new Binary(getContextPosition(ctx), left, Binary.Operator.ARR, right);
        }

        return left;
    }

    @Override
    public Expr visitPostfix_expression2(Postfix_expression2Context ctx) {
        if (ctx.expression() != null) {
            var expr = visitExpression(ctx.expression());
            var right = visitPostfix_expression2(ctx.postfix_expression2());

            if (right != null)
                return new Binary(getContextPosition(ctx), expr, Binary.Operator.ARR, right);
            return expr;
        }

        return null;
    }

    @Override
    public Expr visitAtom_expression(Atom_expressionContext ctx) {
        if (ctx.C_LOGICAL() != null)
            return new Literal(getContextPosition(ctx), ctx.C_LOGICAL().getText(), Atom.Type.LOG);
        if (ctx.C_INTEGER() != null)
            return new Literal(getContextPosition(ctx), ctx.C_INTEGER().getText(), Atom.Type.INT);
        if (ctx.C_STRING() != null)
            return new Literal(getContextPosition(ctx), ctx.C_STRING().getText().replace("'", ""), Atom.Type.STR);
        if (ctx.IDENTIFIER() != null) {
            var name = ctx.IDENTIFIER().getText();
            var expressions = visitAtom_expression2(ctx.atom_expression2());

            if (expressions instanceof Block block) {
                var expressionList = new ArrayList<Expr>(block.expressions);
                return new Call(getContextPosition(ctx),
                        expressionList.isEmpty() ? Optional.empty() : Optional.of(expressionList), name);
            }

            return new Name(getContextPosition(ctx), ctx.IDENTIFIER().getText());
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
        if (ctx.expression() != null)
            return visitExpression(ctx.expression());
        return null;
    }

    @Override
    public Expr visitAtom_expression4(Atom_expression4Context ctx) {
        if (ctx.IF() != null) {
            var condition = visitExpression(ctx.expression(0));
            var thenBlock = visitExpression(ctx.expression(1));
            var elseBlock = visitAtom_expression3(ctx.atom_expression3());

            return new IfThenElse(getContextPosition(ctx), condition, thenBlock,
                    elseBlock != null ? Optional.of(elseBlock) : Optional.empty());
        }
        if (ctx.WHILE() != null) {
            var condition = visitExpression(ctx.expression(0));
            var block = visitExpression(ctx.expression(1));

            return new While(getContextPosition(ctx), condition, block);
        }
        if (ctx.FOR() != null) {
            var identifier = ctx.IDENTIFIER().getText();
            var name = new Name(getContextPosition(ctx), identifier);

            var low = visitExpression(ctx.expression(0));
            var high = visitExpression(ctx.expression(1));
            var step = visitExpression(ctx.expression(2));
            var block = visitExpression(ctx.expression(3));

            return new For(getContextPosition(ctx), name, low, high, step, block);
        }
        if (ctx.expression() != null) {
            var left = visitExpression(ctx.expression(0));
            var right = visitExpression(ctx.expression(1));

            return new Binary(getContextPosition(ctx), left, Binary.Operator.ASSIGN, right);
        }
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
