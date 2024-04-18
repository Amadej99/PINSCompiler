package compiler.parser;

import compiler.lexer.Position;
import compiler.parser.ast.Ast;
import compiler.parser.ast.def.Def;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.VarDef;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class pinsAstVisitor extends pinsBaseVisitor<Ast>{

    @Override
    public Ast visitProgram(pinsParser.ProgramContext ctx) {
        return this.visitDefinitions(ctx.definitions());
    }

    @Override
    public Defs visitDefinitions(pinsParser.DefinitionsContext ctx) {
        var def = this.visitDefinition(ctx.definition());
        return this.visitDefinitions2(ctx.definitions2());
    }

    @Override
    public Defs visitDefinitions2(pinsParser.Definitions2Context ctx) {
        return this.visitDefinitions(ctx.definitions());
    }

    @Override
    public Def visitDefinition(pinsParser.DefinitionContext ctx) {
        System.out.println(ctx);
        return this.visitVariable_definition(ctx.variable_definition());
    }

    @Override
    public Def visitVariable_definition(pinsParser.Variable_definitionContext ctx) {
        var name = ctx.children.get(1).getText();
        var type = this.visitType(ctx.type());

        return new VarDef(getContextPosition(ctx), name, Atom.INT(new Position(1,2,1,2)));
    }

    @Override
    public Type visitType(pinsParser.TypeContext ctx) {
        return Atom.INT(getContextPosition(ctx));
    }

    private Position getContextPosition(ParserRuleContext ctx){
        return new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.stop.getLine(), ctx.stop.getCharPositionInLine());
    }
}
