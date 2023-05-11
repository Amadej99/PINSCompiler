/**
 * @ Author: turk
 * @ Description: Generator vmesne kode.
 */

package compiler.ir;

import static common.RequireNonNull.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.Sequence;
import javax.swing.Icon;

import common.Constants;
import common.VoidOperator;
import compiler.common.Visitor;
import compiler.frm.Access;
import compiler.frm.Frame;
import compiler.frm.Frame.Label;
import compiler.ir.chunk.Chunk;
import compiler.ir.code.IRNode;
import compiler.ir.code.expr.*;
import compiler.ir.code.stmt.*;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.*;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.TypeName;
import compiler.seman.common.NodeDescription;
import compiler.seman.type.type.Type;

public class IRCodeGenerator implements Visitor {
    /**
     * Preslikava iz vozlišč AST v vmesno kodo.
     */
    private NodeDescription<IRNode> imcCode;

    /**
     * Razrešeni klicni zapisi.
     */
    private final NodeDescription<Frame> frames;

    /**
     * Razrešeni dostopi.
     */
    private final NodeDescription<Access> accesses;

    /**
     * Razrešene definicije.
     */
    private final NodeDescription<Def> definitions;

    /**
     * Razrešeni tipi.
     */
    private final NodeDescription<Type> types;

    /**
     * **Rezultat generiranja vmesne kode** - seznam fragmentov.
     */
    public List<Chunk> chunks = new ArrayList<>();

    public IRCodeGenerator(
            NodeDescription<IRNode> imcCode,
            NodeDescription<Frame> frames,
            NodeDescription<Access> accesses,
            NodeDescription<Def> definitions,
            NodeDescription<Type> types) {
        requireNonNull(imcCode, frames, accesses, definitions, types);
        this.types = types;
        this.imcCode = imcCode;
        this.frames = frames;
        this.accesses = accesses;
        this.definitions = definitions;
    }

    private Frame currentFrame;

    @Override
    public void visit(Call call) {
        call.arguments.forEach(arg -> arg.accept(this));

        var currentLevel = currentFrame.staticLevel;
        var def = definitions.valueFor(call).get();

        frames.valueFor(def).ifPresent(frame -> {
            var binopOld = new BinopExpr(NameExpr.SP(), new ConstantExpr(currentFrame.oldFPOffset()), BinopExpr.Operator.SUB);
            var saveFP = new MoveStmt(new MemExpr(binopOld), NameExpr.FP());
            List<IRExpr> args = new ArrayList<>();

            var diff = Math.abs(currentLevel - frame.staticLevel);

            if (diff == 0)
                args.add(new BinopExpr(new ConstantExpr(0), new ConstantExpr(1), BinopExpr.Operator.SUB));

            args.add(currentFrame.staticLevel == 1 ? new ConstantExpr(-1) : NameExpr.FP());
            call.arguments.forEach(arg -> args.add((IRExpr) imcCode.valueFor(arg).get()));

            imcCode.store(new EseqExpr(saveFP, new CallExpr(frame.label, args)), call);
        });
    }

    @Override
    public void visit(Binary binary) {
        binary.left.accept(this);
        binary.right.accept(this);
        var left = imcCode.valueFor(binary.left).get();
        var right = imcCode.valueFor(binary.right).get();

        if (binary.operator.equals(Binary.Operator.ASSIGN)) {
            imcCode.store(new MoveStmt((IRExpr) left, (IRExpr) right), binary);
        } else if (binary.operator.equals(Binary.Operator.ARR)) {
            types.valueFor(binary).ifPresent(binaryValue -> {
                var size = binaryValue.sizeInBytes();
                var offset = new BinopExpr((IRExpr) right, new ConstantExpr(size), BinopExpr.Operator.MUL);
                var location = new BinopExpr((IRExpr) left, offset, BinopExpr.Operator.ADD);

                imcCode.store(new MemExpr(location), binary);
            });
        } else {
            imcCode.store(new BinopExpr((IRExpr) left, (IRExpr) right, BinopExpr.Operator.valueOf(binary.operator.toString())), binary);
        }
    }

    @Override
    public void visit(Block block) {
        block.expressions.forEach(expr -> expr.accept(this));
        //TODO spravi v en line za Tonija <3
        imcCode.store(new EseqExpr(new SeqStmt(block.expressions.stream().limit(block.expressions.size() - 1).map(expr -> imcCode.valueFor(expr).get() instanceof IRStmt irStmt ? irStmt : new ExpStmt((IRExpr) imcCode.valueFor(expr).get())).collect(Collectors.toList())), (IRExpr) imcCode.valueFor(block.expressions.get(block.expressions.size() - 1)).get()), block);
    }

    @Override
    public void visit(For forLoop) {
        forLoop.counter.accept(this);
        forLoop.low.accept(this);
        forLoop.high.accept(this);
        forLoop.step.accept(this);
        forLoop.body.accept(this);

        var statements = new ArrayList<IRStmt>();

        var label0 = new LabelStmt(Label.nextAnonymous());
        var label1 = new LabelStmt(Label.nextAnonymous());
        var label2 = new LabelStmt(Label.nextAnonymous());

        var cond = new BinopExpr((IRExpr) imcCode.valueFor(forLoop.counter).get(), (IRExpr) imcCode.valueFor(forLoop.high).get(), BinopExpr.Operator.LT);

        statements.add(new MoveStmt(new MemExpr((IRExpr) imcCode.valueFor(forLoop.counter).get()), (IRExpr) imcCode.valueFor(forLoop.low).get()));
        statements.add(label0);
        statements.add(new CJumpStmt(cond, label1.label, label2.label));
        statements.add(label1);
        statements.add(new ExpStmt((IRExpr) imcCode.valueFor(forLoop.body).get()));
        statements.add(new MoveStmt((IRExpr) imcCode.valueFor(forLoop.counter).get(), new BinopExpr(new MemExpr((IRExpr) imcCode.valueFor(forLoop.counter).get()), (IRExpr) imcCode.valueFor(forLoop.step).get(), BinopExpr.Operator.ADD)));
        statements.add(new JumpStmt(label0.label));
        statements.add(label2);

        imcCode.store(new SeqStmt(statements), forLoop);
    }

    @Override
    public void visit(Name name) {
        definitions.valueFor(name).ifPresent(nameValue -> {
            accesses.valueFor(nameValue).ifPresent(accessValue -> {
                if (accessValue instanceof Access.Global globalAccess)
                    imcCode.store(new NameExpr(globalAccess.label), name);

                if (accessValue instanceof Access.Parameter parameterAccess)
                    imcCode.store(new MemExpr(new BinopExpr(NameExpr.FP(), new ConstantExpr(parameterAccess.offset), BinopExpr.Operator.ADD)), name);

                if (accessValue instanceof Access.Local localAccess) {
                    if (currentFrame.staticLevel == localAccess.staticLevel)
                        imcCode.store(new MemExpr(new BinopExpr(NameExpr.FP(), new ConstantExpr(localAccess.offset), BinopExpr.Operator.ADD)), name);

                    else {
                        var diff = Math.abs(localAccess.staticLevel - currentFrame.staticLevel);
                        var location = NameExpr.FP();
                        IRExpr newLocation = null;

                        if (diff == 1)
                            imcCode.store(new MemExpr(new BinopExpr(location, new ConstantExpr(localAccess.offset), BinopExpr.Operator.ADD)), name);

                        else {
                            while (diff > 0) {
                                newLocation = new MemExpr(location);
                                diff--;
                            }

                            imcCode.store(new MemExpr(new BinopExpr(newLocation, new ConstantExpr(localAccess.offset), BinopExpr.Operator.ADD)), name);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);
        ifThenElse.thenExpression.accept(this);
        ifThenElse.elseExpression.ifPresent(expr -> expr.accept(this));

        var statements = new ArrayList<IRStmt>();

        var label1 = new LabelStmt(Label.nextAnonymous());
        var label2 = new LabelStmt(Label.nextAnonymous());
        var label3 = new LabelStmt(Label.nextAnonymous());

        imcCode.valueFor(ifThenElse.condition).ifPresent(ifValue -> {
            imcCode.valueFor(ifThenElse.thenExpression).ifPresent(thenValue -> {
                var thenExpr = new ExpStmt((IRExpr) thenValue);

                if (ifThenElse.elseExpression.isPresent()) {
                    statements.add(new CJumpStmt((IRExpr) ifValue, label1.label, label2.label));
                    statements.add(label1);
                    statements.add(thenExpr);
                    statements.add(new JumpStmt(label3.label));
                    statements.add(label2);
                    statements.add(new ExpStmt((IRExpr) imcCode.valueFor(ifThenElse.elseExpression.get()).get()));
                    statements.add(label3);
                } else {
                    statements.add(new CJumpStmt((IRExpr) ifValue, label1.label, label3.label));
                    statements.add(label1);
                    statements.add(thenExpr);
                    statements.add(label3);
                }

                imcCode.store(new SeqStmt(statements), ifThenElse);
            });
        });
    }

    @Override
    public void visit(Literal literal) {
        types.valueFor(literal).ifPresent(type -> {
            if (type.isInt())
                imcCode.store(new ConstantExpr(Integer.parseInt(literal.value)), literal);
            else if (type.isLog())
                imcCode.store(new ConstantExpr(literal.value.equals("true") ? 1 : 0), literal);
            else if (type.isStr())
                chunks.add(new Chunk.DataChunk(new Access.Global(4, Label.nextAnonymous()), literal.value));
        });
    }

    @Override
    public void visit(Unary unary) {
        unary.expr.accept(this);
        imcCode.valueFor(unary).ifPresent(unaryValue -> {
            if (unary.operator.equals(Unary.Operator.ADD))
                imcCode.store(new BinopExpr(new ConstantExpr(0), (IRExpr) unaryValue, BinopExpr.Operator.ADD), unary);
            else if (unary.operator.equals(Unary.Operator.SUB))
                imcCode.store(new BinopExpr(new ConstantExpr(0), (IRExpr) unaryValue, BinopExpr.Operator.SUB), unary);
            else if (unary.operator.equals(Unary.Operator.NOT))
                imcCode.store(new ConstantExpr(((ConstantExpr) unaryValue).constant == 0 ? 1 : 0), unary);
        });
    }

    @Override
    public void visit(While whileLoop) {
        whileLoop.condition.accept(this);
        whileLoop.body.accept(this);

        var statements = new ArrayList<IRStmt>();

        var label0 = new LabelStmt(Label.nextAnonymous());
        var label1 = new LabelStmt(Label.nextAnonymous());
        var label2 = new LabelStmt(Label.nextAnonymous());

        statements.add(label0);
        statements.add(new CJumpStmt((IRExpr) imcCode.valueFor(whileLoop.condition).get(), label1.label, label2.label));
        statements.add(label1);
        statements.add(new ExpStmt((IRExpr) imcCode.valueFor(whileLoop.body).get()));
        statements.add(new JumpStmt(label0.label));
        statements.add(label2);

        imcCode.store(new SeqStmt(statements), whileLoop);
    }

    @Override
    public void visit(Where where) {
        where.defs.accept(this);
        where.expr.accept(this);
    }

    @Override
    public void visit(Defs defs) {
        defs.definitions.forEach(def -> def.accept(this));
    }

    @Override
    public void visit(FunDef funDef) {
        this.currentFrame = frames.valueFor(funDef).get();
        funDef.body.accept(this);

        imcCode.valueFor(funDef.body).ifPresent(bodyValue -> {
            if (bodyValue instanceof IRExpr irExpr)
                chunks.add(new Chunk.CodeChunk(currentFrame, new MoveStmt(new MemExpr(NameExpr.FP()), irExpr)));
            else if (bodyValue instanceof IRStmt irStmt)
                chunks.add(new Chunk.CodeChunk(currentFrame, irStmt));
        });
    }

    @Override
    public void visit(TypeDef typeDef) {
    }

    @Override
    public void visit(VarDef varDef) {
        accesses.valueFor(varDef).ifPresent(varValue -> {
            if (varValue instanceof Access.Global varGlobal) {
                chunks.add(new Chunk.GlobalChunk(varGlobal));
            }
        });
    }

    @Override
    public void visit(Parameter parameter) {
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
