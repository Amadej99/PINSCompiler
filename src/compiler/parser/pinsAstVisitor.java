package compiler.parser;

import compiler.lexer.Position;
import compiler.parser.pinsParser.DefinitionContext;
import compiler.parser.pinsParser.Definitions2Context;
import compiler.parser.pinsParser.DefinitionsContext;
import compiler.parser.pinsParser.ProgramContext;
import compiler.parser.pinsParser.TypeContext;
import compiler.parser.pinsParser.Type_definitionContext;
import compiler.parser.pinsParser.Variable_definitionContext;
import compiler.parser.ast.Ast;
import compiler.parser.ast.def.Def;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.FunDef;
import compiler.parser.ast.def.FunDef.Parameters;
import compiler.parser.ast.def.FunDef.Parameters.Parameter;
import compiler.parser.ast.def.FunDef.Parameters.VarArg;
import compiler.parser.ast.def.TypeDef;
import compiler.parser.ast.def.VarDef;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.Type;
import compiler.parser.ast.type.TypeName;
import org.antlr.v4.runtime.ParserRuleContext;

import static compiler.parser.pinsParser.*;

import java.util.ArrayList;
import java.util.Optional;

public class pinsAstVisitor extends pinsBaseVisitor<Ast>{

    @Override
    public Ast visitProgram(ProgramContext ctx) {
        return visitDefinitions(ctx.definitions());
    }

    @Override
    public Defs visitDefinitions(DefinitionsContext ctx) {
        var definitions = new ArrayList<Def>();
        definitions.add(this.visitDefinition(ctx.definition()));
        var definitions2 = this.visitDefinitions2(ctx.definitions2());

        if(definitions2 != null)
            definitions.addAll(definitions2.definitions);

        return new Defs(getContextPosition(ctx), definitions);
    }

    @Override
    public Defs visitDefinitions2(Definitions2Context ctx) {
        if(ctx.definitions() == null)
            return null;
        return this.visitDefinitions(ctx.definitions());
    }

    @Override
    public Def visitDefinition(DefinitionContext ctx) {
        if(ctx.variable_definition() != null)
            return this.visitVariable_definition(ctx.variable_definition());
        if(ctx.type_definition() != null)
            return this.visitType_definition(ctx.type_definition());
        if(ctx.function_declaration() != null){
            return this.visitFunction_declaration(ctx.function_declaration());
        }
        return null;
    }

    @Override
    public FunDef visitFunction_declaration(Function_declarationContext ctx){
        var name = ctx.identifier().getText();
        var parameters = this.visitParameters(ctx.parameters());
        var type = this.visitType(ctx.type());

        var isVarArg = parameters.definitions.stream().anyMatch(parameter -> parameter instanceof VarArg);

        return new FunDef(getContextPosition(ctx), name, parameters.definitions != null ? Optional.of(parameters) : Optional.empty(), type, Optional.empty(), isVarArg);
    }

    @Override
    public FunDef visitFunction_definition(Function_definitionContext ctx){
        var name = ctx.identifier().getText();
        var parameters = this.visitParameters(ctx.parameters());
        var type = this.visitType(ctx.type());
        var expr = this.visitExpression(ctx.expression());

        var isVarArg = parameters.definitions.stream().anyMatch(parameter -> parameter instanceof VarArg);

        return null;
    }

    @Override
    public Parameters visitParameters(ParametersContext ctx){
        var parameters = new ArrayList<Def>();
        parameters.add(this.visitParameter(ctx.parameter()));
        var parameters2 = this.visitParameters2(ctx.parameters2());

        if(parameters2.definitions != null){
            parameters.stream().forEach(parameter -> {
                parameters.add((Parameter) parameter);
            });
        }

        return new Parameters(getContextPosition(ctx), parameters);
    }

    @Override
    public Parameters visitParameters2(Parameters2Context ctx){
        var parameters = new ArrayList<Parameter>();
        if(ctx.parameter() != null){
            parameters.add(this.visitParameter(ctx.parameter()));
            return this.visitParameters2(ctx.parameters2());

        }
        else{
            return new Parameters(null, null);
        }
        
    }

    @Override
    public Parameter visitParameter(ParameterContext ctx){
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
