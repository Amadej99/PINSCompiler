/**
 * @ Author: turk
 * @ Description: Preverjanje in razreševanje imen.
 */

package compiler.seman.name;

import static common.RequireNonNull.requireNonNull;

import common.Report;
import compiler.common.Visitor;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.*;
import compiler.parser.ast.type.*;
import compiler.seman.common.NodeDescription;
import compiler.seman.name.env.SymbolTable;
import compiler.seman.name.env.SymbolTable.DefinitionAlreadyExistsException;

public class NameChecker implements Visitor {
    /**
     * Opis vozlišč, ki jih povežemo z njihovimi
     * definicijami.
     */
    private NodeDescription<Def> definitions;

    /**
     * Simbolna tabela.
     */
    private SymbolTable symbolTable;

    /**
     * Ustvari nov razreševalnik imen.
     */
    public NameChecker(
            NodeDescription<Def> definitions,
            SymbolTable symbolTable) {
        requireNonNull(definitions, symbolTable);
        this.definitions = definitions;
        this.symbolTable = symbolTable;
    }

    @Override
    public void visit(Call call) {
        var def = symbolTable.definitionFor(call.name);
        if (!(def.get() instanceof FunDef))
            Report.error(call.name + " ni funkcija!");
        def.ifPresent(value -> definitions.store(value, call));
        call.arguments.stream().forEach(argument -> argument.accept(this));
    }

    @Override
    public void visit(Binary binary) {
        binary.left.accept(this);
        binary.right.accept(this);
    }

    @Override
    public void visit(Block block) {
        block.expressions.stream().forEach(definition -> definition.accept(this));
    }

    @Override
    public void visit(For forLoop) {
        forLoop.counter.accept(this);
        forLoop.low.accept(this);
        forLoop.high.accept(this);
        forLoop.step.accept(this);
        forLoop.body.accept(this);
    }

    @Override
    public void visit(Name name) {
        var def = symbolTable.definitionFor(name.name);
        if (def.get() instanceof FunDef)
            Report.error(name.position + " " + name.name + " je funkcija, ne spremenljivka!");
        if (def.get() instanceof TypeDef)
            Report.error(name.position + " " + name.name + " je tip, ne spremenljivka!");
        def.ifPresentOrElse(value -> definitions.store(value, name),
                () -> Report.error("Nedefinirana spremenljivka " + name.name));
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);
        ifThenElse.thenExpression.accept(this);
        ifThenElse.elseExpression.ifPresent(expression -> expression.accept(this));
    }

    @Override
    public void visit(Literal literal) {
    }

    @Override
    public void visit(Unary unary) {
        unary.expr.accept(this);
    }

    @Override
    public void visit(While whileLoop) {
        whileLoop.condition.accept(this);
        whileLoop.body.accept(this);
    }

    @Override
    public void visit(Where where) {
        symbolTable.inNewScope(() -> {
            where.expr.accept(this);
            where.defs.accept(this);
        });
    }

    @Override
    public void visit(Defs defs) {
        defs.definitions.stream().forEach(def -> {
            try {
                symbolTable.insert(def);
            } catch (DefinitionAlreadyExistsException e) {
                Report.error("Error in NameChecker.visit(Defs defs)");
            }
        });
        defs.definitions.stream().forEach(def -> def.accept(this));
    }

    @Override
    public void visit(FunDef funDef) {
        symbolTable.inNewScope(() -> {
            funDef.parameters.stream().forEach(parameter -> parameter.accept(this));
            funDef.type.accept(this);
            funDef.body.accept(this);
        });
    }

    @Override
    public void visit(TypeDef typeDef) {
        typeDef.type.accept(this);
    }

    @Override
    public void visit(VarDef varDef) {
        varDef.type.accept(this);
    }

    @Override
    public void visit(Parameter parameter) {
        try {
            symbolTable.insert(parameter);
            parameter.type.accept(this);
        } catch (DefinitionAlreadyExistsException e) {
            Report.error("Error in NameChecker.visit(Parameter parameter)");
        }
    }

    @Override
    public void visit(Array array) {
        array.type.accept(this);
    }

    @Override
    public void visit(Atom atom) {
    }

    @Override
    public void visit(TypeName name) {
        var def = symbolTable.definitionFor(name.identifier);
        if ((def.get() instanceof VarDef)) {
            Report.error(name.position + " " + name.identifier + " je spremenljvka, ne tip!");
        }
        if ((def.get() instanceof FunDef)) {
            Report.error(name.position + " " + name.identifier + " je funkcija, ne tip!");
        }
        if ((def.get() instanceof Parameter)) {
            Report.error(name.position + " " + name.identifier + " je parameter, ne tip!");
        }

        def.ifPresentOrElse(value -> definitions.store(value, name),
                () -> Report.error(name.position + " Nedefiniran tip " + name.identifier));
    }
}
