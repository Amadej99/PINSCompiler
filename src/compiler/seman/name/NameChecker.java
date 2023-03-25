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
        try {
            var def = symbolTable.definitionFor(call.name);
            def.ifPresent(value -> definitions.store(value, call));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        def.ifPresent(value -> definitions.store(value, name));
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);
        ifThenElse.thenExpression.accept(this);
        ifThenElse.elseExpression.ifPresent(expression -> expression.accept(this));
    }

    @Override
    public void visit(Literal literal) {
        // What do we do with this?
        literal.accept(this);
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
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Defs defs) {
        try {
            defs.definitions.stream().forEach(def -> {
                try {
                    symbolTable.insert(def);
                } catch (DefinitionAlreadyExistsException e) {
                    Report.error("Error in NameChecker.visit(Defs defs)");
                }
            });
            defs.definitions.stream().forEach(def -> def.accept(this));
        } catch (Exception e) {
            Report.error("Error in NameChecker.visit(Defs defs)");
        }
    }

    @Override
    public void visit(FunDef funDef) {
        try {
            if (symbolTable.getCurrentScope() != 0)
                symbolTable.insert(funDef);
            symbolTable.inNewScope(() -> {
                funDef.parameters.stream().forEach(parameter -> parameter.accept(this));
                funDef.body.accept(this);
            });
        } catch (DefinitionAlreadyExistsException e) {
            Report.error("Function " + funDef.name + " already defined " + funDef.position);
        }
    }

    @Override
    public void visit(TypeDef typeDef) {
        try {
            if (symbolTable.getCurrentScope() != 0)
                symbolTable.insert(typeDef);
        } catch (DefinitionAlreadyExistsException e) {
            Report.error("Error in NameChecker.visit(TypeDef typeDef)");
        }
    }

    @Override
    public void visit(VarDef varDef) {
        try {
            if (symbolTable.getCurrentScope() != 0)
                symbolTable.insert(varDef);
        } catch (DefinitionAlreadyExistsException e) {
            Report.error("Error in NameChecker definition already exists " + varDef.name + " " + varDef.position);
        }
    }

    @Override
    public void visit(Parameter parameter) {
        try {
            symbolTable.insert(parameter);
        } catch (DefinitionAlreadyExistsException e) {
            Report.error("Error in NameChecker.visit(Parameter parameter)");
        }
    }

    @Override
    public void visit(Array array) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Atom atom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(TypeName name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
}
