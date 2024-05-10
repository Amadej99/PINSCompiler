package compiler.parser;

import compiler.lexer.Position;
import compiler.parser.ast.Ast;
import compiler.parser.ast.def.Def;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.TypeDef;
import compiler.parser.ast.def.VarDef;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.Type;
import compiler.parser.ast.type.TypeName;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;

public class pinsAstVisitor extends pinsBaseVisitor<Ast>{

    @Override
    public Ast visitProgram(pinsParser.ProgramContext ctx) {
        return visitDefinitions(ctx.definitions());
    }

    @Override
    public Defs visitDefinitions(pinsParser.DefinitionsContext ctx) {
        var definitions = new ArrayList<Def>();
        definitions.add(this.visitDefinition(ctx.definition()));
        var definitions2 = this.visitDefinitions2(ctx.definitions2());

        if(definitions2 != null)
            definitions.addAll(definitions2.definitions);

        return new Defs(getContextPosition(ctx), definitions);
    }

    @Override
    public Defs visitDefinitions2(pinsParser.Definitions2Context ctx) {
        if(ctx.definitions() == null)
            return null;
        return this.visitDefinitions(ctx.definitions());
    }

    @Override
    public Def visitDefinition(pinsParser.DefinitionContext ctx) {
        if(ctx.variable_definition() != null)
            return this.visitVariable_definition(ctx.variable_definition());
        if(ctx.type_definition() != null)
            return this.visitType_definition(ctx.type_definition());
        return null;
    }

    @Override
    public VarDef visitVariable_definition(pinsParser.Variable_definitionContext ctx) {
        var name = ctx.children.get(1).getText();
        var type = this.visitType(ctx.type());
        return new VarDef(getContextPosition(ctx), name, type);
    }

    @Override
    public TypeDef visitType_definition(pinsParser.Type_definitionContext ctx) {
        var name = ctx.identifier().getText();
        var type = this.visitType(ctx.type());
        return new TypeDef(getContextPosition(ctx), name, type);
    }

    @Override
    public Type visitType(pinsParser.TypeContext ctx) {
        if(ctx.identifier() != null)
            return new TypeName(getContextPosition(ctx), ctx.identifier().getText());
        if(ctx.integer() != null)
            return Atom.INT(getContextPosition(ctx));
        if(ctx.logical() != null)
            return Atom.LOG(getContextPosition(ctx));
        if(ctx.string() != null)
            return Atom.STR(getContextPosition(ctx));
        if(ctx.arr() != null){
            var type = this.visitType(ctx.type());
            var size = Integer.parseInt(ctx.children.get(2).getText());
            return new Array(getContextPosition(ctx), size, type);
        }
        return null;
    }

    private Position getContextPosition(ParserRuleContext ctx){
        return new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.stop.getLine(), ctx.stop.getCharPositionInLine());
    }
}
