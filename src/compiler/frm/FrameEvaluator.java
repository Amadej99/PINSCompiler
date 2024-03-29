/**
 * @ Author: turk
 * @ Description: Analizator klicnih zapisov.
 */

package compiler.frm;

import static common.RequireNonNull.requireNonNull;

import common.Constants;
import common.VoidOperator;
import compiler.common.Visitor;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.*;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.TypeName;
import compiler.seman.common.NodeDescription;
import compiler.seman.type.type.Type;

import java.util.Stack;

public class FrameEvaluator implements Visitor {
    /**
     * Opis definicij funkcij in njihovih klicnih zapisov.
     */
    private NodeDescription<Frame> frames;

    /**
     * Opis definicij spremenljivk in njihovih dostopov.
     */
    private NodeDescription<Access> accesses;

    /**
     * Opis vozlišč in njihovih definicij.
     */
    private final NodeDescription<Def> definitions;

    /**
     * Opis vozlišč in njihovih podatkovnih tipov.
     */
    private final NodeDescription<Type> types;

    private int staticLevel = 0;
    private Stack<Frame.Builder> builders = new Stack<>();

    private void inNewScope(VoidOperator op) {
        requireNonNull(op);
        staticLevel++;
        op.apply();
        staticLevel--;
    }

    public FrameEvaluator(
            NodeDescription<Frame> frames,
            NodeDescription<Access> accesses,
            NodeDescription<Def> definitions,
            NodeDescription<Type> types) {
        requireNonNull(frames, accesses, definitions, types);
        this.frames = frames;
        this.accesses = accesses;
        this.definitions = definitions;
        this.types = types;
    }

    @Override
    public void visit(Call call) {
        // Sprejmi argumente
        call.arguments.forEach(arg -> arg.accept(this));
        int argumentsSize = call.arguments.stream().map(arg -> types.valueFor(arg).get())
                .mapToInt(type -> type.sizeInBytesAsParam()).sum();
        // Dodaj prostor za argumente in static link
        builders.peek().addFunctionCall(argumentsSize + Constants.WordSize);
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
        forLoop.high.accept(this);
        forLoop.low.accept(this);
        forLoop.step.accept(this);
        forLoop.body.accept(this);
    }

    @Override
    public void visit(Name name) {
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);
        ifThenElse.thenExpression.accept(this);
        ifThenElse.elseExpression.ifPresent(expr -> expr.accept(this));

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
        where.defs.accept(this);
        where.expr.accept(this);
    }

    @Override
    public void visit(Defs defs) {
        defs.definitions.stream().forEach(def -> def.accept(this));
    }

    @Override
    public void visit(FunDef funDef) {
        inNewScope(() -> {
            // Ce je funkcija globalna jo ustvarimo kot poimenovano, sicer kot anonimno
            Frame.Builder builder = new Frame.Builder(
                    staticLevel > 1 ? Frame.Label.nextAnonymous() : Frame.Label.named(funDef.name), staticLevel);
            builders.push(builder);

            // V parametrih dodaj prostor za static link
            builders.peek().addParameter(Constants.WordSize);

            // Sprejmi parametre in telo funkcije
            funDef.parameters.stream().forEach(parameter -> parameter.accept(this));
            funDef.body.accept(this);

            // Klicni zapis shrani v seznam klicnih zapisov
            frames.store(builder.build(), funDef);
            builders.pop();
        });
    }

    @Override
    public void visit(TypeDef typeDef) {
    }

    @Override
    public void visit(VarDef varDef) {
        // Sprejmi tip in dodaj prostor za spremenljivko
        types.valueFor(varDef).ifPresent(type -> {
            int size = type.sizeInBytes();
            Access acc;
            // Ce je spremenljivka lokalna, jo dodamo v trenutni klicni zapis, sicer jo
            // dodamo kot globalno
            if (staticLevel > 0)
                acc = new Access.Local(size, builders.peek().addLocalVariable(size), staticLevel);
            else
                acc = new Access.Global(size, Frame.Label.named(varDef.name));
            accesses.store(acc, varDef);
        });
    }

    @Override
    public void visit(Parameter parameter) {
        // V klicnem zapisu dodaj prostor za parameter, hkrati pa ustvari Access
        types.valueFor(parameter).ifPresent(type -> {
            Access.Parameter par = new Access.Parameter(type.sizeInBytesAsParam(),
                    builders.peek().addParameter(type.sizeInBytesAsParam()), builders.peek().staticLevel);
            accesses.store(par, parameter);
        });
    }

    @Override
    public void visit(Array array) {
    }

    @Override
    public void visit(Atom atom) {
    }

    @Override
    public void visit(TypeName name) {
    }
}
