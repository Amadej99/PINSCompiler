package compiler.ir;

import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import common.Report;

import static org.bytedeco.llvm.global.LLVM.*;

import org.bytedeco.javacpp.PointerPointer;

import compiler.common.Visitor;
import compiler.parser.ast.def.Def;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.FunDef;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.def.TypeDef;
import compiler.parser.ast.def.VarDef;
import compiler.parser.ast.expr.Binary;
import compiler.parser.ast.expr.Block;
import compiler.parser.ast.expr.Call;
import compiler.parser.ast.expr.For;
import compiler.parser.ast.expr.IfThenElse;
import compiler.parser.ast.expr.Literal;
import compiler.parser.ast.expr.Name;
import compiler.parser.ast.expr.Unary;
import compiler.parser.ast.expr.Where;
import compiler.parser.ast.expr.While;
import compiler.parser.ast.type.Array;
import compiler.parser.ast.type.Atom;
import compiler.parser.ast.type.TypeName;
import compiler.seman.common.NodeDescription;
import compiler.seman.type.type.Type;
import java.util.stream.IntStream;
import java.util.HashMap;

public class LLVMCodeGenerator implements Visitor {

    /**
     * Globalni kontekst prevajanja.
     */
    public LLVMContextRef context;

    /**
     * Enota prevajanja.
     */
    public LLVMModuleRef module;

    /**
     * Izdelovalec vmesne kode.
     */
    public LLVMBuilderRef builder;

    /**
     * Razrešene definicije.
     */
    public NodeDescription<Def> definitions;

    /**
     * Razrešeni tipi.
     */
    public NodeDescription<Type> types;

    /**
     * Preslikava AST vozlišče -> LLVMValueRef
     */
    public NodeDescription<LLVMValueRef> IRNodes;

    /**
     * 
     */
    public HashMap<String, LLVMValueRef> NamedValues;

    /**
     * 
     */
    public HashMap<String, LLVMTypeRef> functionTypes;

    /**
     * 
     * @param context
     * @param module
     * @param builder
     * @param types
     */
    public LLVMCodeGenerator(LLVMContextRef context, LLVMModuleRef module, LLVMBuilderRef builder,
            NodeDescription<Type> types) {
        this.context = context;
        this.module = module;
        this.builder = builder;
        this.types = types;
        this.IRNodes = new NodeDescription<LLVMValueRef>();
        this.NamedValues = new HashMap<String, LLVMValueRef>();
        this.functionTypes = new HashMap<>();
    }

    @Override
    public void visit(Call call) {
        var calledFunction = LLVMGetNamedFunction(module, call.name);

        call.arguments.forEach(argument -> argument.accept(this));

        var calledFunctionParameterSize = LLVMCountParams(calledFunction);

        var arguments = new PointerPointer<>(calledFunctionParameterSize);

        IntStream.range(0, calledFunctionParameterSize).forEach(i -> {
            IRNodes.valueFor(call.arguments.get(i)).ifPresent(value -> {
                arguments.put(i, value);
            });
        });

        IRNodes.store(LLVMBuildCall2(builder, functionTypes.get(call.name), calledFunction, arguments,
                calledFunctionParameterSize, call.name), call);
    }

    @Override
    public void visit(Binary binary) {
        if (binary.operator.equals(Binary.Operator.ASSIGN)) {
            if ((binary.left instanceof Name name)) {
                binary.right.accept(this);
                var right = IRNodes.valueFor(binary.right).get();
                var address = NamedValues.get(name.name);
                LLVMBuildStore(builder, right, address);
                IRNodes.store(right, binary);
                return;
            } else
                Report.error("Leva stran operacije prirejanja mora biti spremenljivka!");
        }

        binary.left.accept(this);
        binary.right.accept(this);

        var left = IRNodes.valueFor(binary.left).get();
        var right = IRNodes.valueFor(binary.right).get();

        if (binary.operator.equals(Binary.Operator.ADD)) {
            IRNodes.store(LLVMBuildAdd(builder, left, right, "add"), binary);
        } else if (binary.operator.equals(Binary.Operator.SUB)) {
            IRNodes.store(LLVMBuildSub(builder, left, right, "sub"), binary);
        } else if (binary.operator.equals(Binary.Operator.MUL)) {
            IRNodes.store(LLVMBuildMul(builder, left, right, "mul"), binary);
        } else if (binary.operator.equals(Binary.Operator.DIV)) {
            throw new UnsupportedOperationException("Unimplemented div!");
        } else if (binary.operator.equals(Binary.Operator.EQ)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntEQ, left, right, "cmpInt"), binary);
        } else if (binary.operator.equals(Binary.Operator.NEQ)){
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntNE, left, right, "cmpInt"), binary);
        } else if (binary.operator.equals(Binary.Operator.LT)){
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSLT, left, right, "cmpInt"), binary);
        } else if (binary.operator.equals(Binary.Operator.LEQ)){
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSLE, left, right, "cmpInt"), binary);
        } else if (binary.operator.equals(Binary.Operator.GT)){
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSGT, left, right, "cmpInt"), binary);
        } else if (binary.operator.equals(Binary.Operator.GEQ)){
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSGE, left, right, "cmpInt"), binary);
        }
    }

    @Override
    public void visit(Block block) {
        block.expressions.forEach(expr -> expr.accept(this));
        var lastExpression = IRNodes.valueFor(block.expressions.getLast()).get();
        IRNodes.store(lastExpression, block);
    }

    @Override
    public void visit(For forLoop) {
        // forLoop.counter.accept(this);
        forLoop.low.accept(this);

        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);
        var loop = LLVMCreateBasicBlockInContext(context, "loop");

        var counterAddress = NamedValues.get(forLoop.counter.name);
        LLVMBuildStore(builder, IRNodes.valueFor(forLoop.low).get(), counterAddress);
        var counterValue = LLVMBuildLoad2(builder, LLVMInt32TypeInContext(context), counterAddress, forLoop.counter.name + " value");

        LLVMPositionBuilderAtEnd(builder, loop);
        var phi = LLVMBuildPhi(builder, LLVMInt32TypeInContext(context), "loopPhi");
        var phiValues = new PointerPointer<>();
        phiValues.put(0, counterValue);
        var phiBlocks = new PointerPointer<>();
        phiBlocks.put(0, currentBlock);

        var oldCounterNameValue = NamedValues.get(forLoop.counter.name);
        NamedValues.remove(forLoop.counter.name);
        NamedValues.put(forLoop.counter.name, phi);

        forLoop.body.accept(this);
        forLoop.step.accept(this);

        var nextCounterValue = LLVMBuildAdd(builder, counterValue, IRNodes.valueFor(forLoop.step).get(), "nextValue");

        forLoop.high.accept(this);

        var endForBlock = LLVMGetInsertBlock(builder);

        var afterBlock = LLVMAppendBasicBlock(currentFunction, "afterLoop");
        LLVMBuildCondBr(builder, IRNodes.valueFor(forLoop.high).get(), loop, afterBlock);
        LLVMPositionBuilderAtEnd(builder, afterBlock);

        phiValues.put(1, nextCounterValue);
        phiBlocks.put(1, endForBlock);

        NamedValues.remove(forLoop.counter.name);
        if(oldCounterNameValue != null)
            NamedValues.put(forLoop.counter.name, oldCounterNameValue);
    }

    @Override
    public void visit(Name name) {
        var alloca = NamedValues.get(name.name);
        IRNodes.store(LLVMBuildLoad2(builder, LLVMInt32TypeInContext(context), alloca, name.name), name);
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);

        var condition = IRNodes.valueFor(ifThenElse.condition).get();

        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);

        var thenBlock = LLVMAppendBasicBlockInContext(context, currentFunction, "then");
        var elseBlock = LLVMCreateBasicBlockInContext(context, "else");
        var exitBlock = LLVMCreateBasicBlockInContext(context, "exit");

        if(ifThenElse.elseExpression.isPresent())
            LLVMBuildCondBr(builder, condition, thenBlock, elseBlock);
        else
            LLVMBuildCondBr(builder, condition, thenBlock, exitBlock);

        LLVMPositionBuilderAtEnd(builder, thenBlock);
        ifThenElse.thenExpression.accept(this);
        LLVMBuildBr(builder, exitBlock);
        thenBlock = LLVMGetInsertBlock(builder);

        if(ifThenElse.elseExpression.isPresent()) {
            LLVMAppendExistingBasicBlock(currentFunction, elseBlock);
            LLVMPositionBuilderAtEnd(builder, elseBlock);
            ifThenElse.elseExpression.ifPresent(expr -> expr.accept(this));
            LLVMBuildBr(builder, exitBlock);
            elseBlock = LLVMGetInsertBlock(builder);
        }

        LLVMAppendExistingBasicBlock(currentFunction, exitBlock);
        LLVMPositionBuilderAtEnd(builder, exitBlock);

        if(ifThenElse.elseExpression.isPresent()) {
            var phi = LLVMBuildPhi(builder, LLVMInt32TypeInContext(context), "phi");
            var phiValues = new PointerPointer<>(2);
            phiValues.put(0, IRNodes.valueFor(ifThenElse.thenExpression).get());
            ifThenElse.elseExpression
                    .ifPresent(elseExpr -> IRNodes.valueFor(elseExpr).ifPresent(elseValue -> phiValues.put(1, elseValue)));

            var phiBlocks = new PointerPointer<>(2);
            phiBlocks.put(0, thenBlock);
            if (ifThenElse.elseExpression.isPresent()) {
                phiBlocks.put(1, elseBlock);
            }

            LLVMAddIncoming(phi, phiValues, phiBlocks, ifThenElse.elseExpression.isPresent() ? 2 : 1);
        }
    }

    @Override
    public void visit(Literal literal) {
        types.valueFor(literal).ifPresent(type -> {
            if (type.isInt())
                IRNodes.store(LLVMConstInt(LLVMInt32TypeInContext(context), Integer.parseInt(literal.value), 0),
                        literal);
            else if (type.isLog())
                IRNodes.store(LLVMConstInt(LLVMInt1Type(), literal.value.equals("true") ? 1 : 0, 0), literal);
            else if (type.isStr())
                IRNodes.store(LLVMConstString(literal.value, literal.value.length(), 0), literal);
        });
    }

    @Override
    public void visit(Unary unary) {
        unary.expr.accept(this);
        IRNodes.valueFor(unary.expr).ifPresent(value -> {
            if (unary.operator.equals(Unary.Operator.ADD))
                IRNodes.store(value, unary);
            else if (unary.operator.equals(Unary.Operator.SUB)) {
                IRNodes.store(
                        LLVMBuildSub(builder, LLVMConstInt(LLVMInt32TypeInContext(context), 0, 1), value, "-Value"),
                        unary);
            } else if (unary.operator.equals(Unary.Operator.NOT)) {
                IRNodes.store(
                        LLVMBuildSub(builder, LLVMConstInt(LLVMInt32TypeInContext(context), 1, 1), value, "!Value"),
                        unary);
            }
        });
    }

    @Override
    public void visit(While whileLoop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Where where) {
        where.defs.accept(this);
        where.expr.accept(this);
        var whereReturn = IRNodes.valueFor(where.expr).get();
        IRNodes.store(whereReturn, where);
    }

    @Override
    public void visit(Defs defs) {
        defs.definitions.forEach(def -> def.accept(this));
    }

    @Override
    public void visit(FunDef funDef) {
        var parameterTypes = new PointerPointer<>(funDef.parameters.size());

        IntStream.range(0, funDef.parameters.size()).forEach(i -> {
            Parameter parameter = funDef.parameters.get(i);
            types.valueFor(parameter.type).ifPresent(type -> {
                if (type.isInt())
                    parameterTypes.put(i, LLVMInt32TypeInContext(context));
                // TODO: Ostali podatkovni tipi.
            });
        });

        var functionType = LLVMFunctionType(LLVMInt32TypeInContext(context), parameterTypes, funDef.parameters.size(),
                0);

        functionTypes.put(funDef.name, functionType);
        var function = LLVMAddFunction(module, funDef.name, functionType);

        var entry = LLVMAppendBasicBlockInContext(context, function, "entry");
        LLVMPositionBuilderAtEnd(builder, entry);

        NamedValues.clear();

        IntStream.range(0, funDef.parameters.size()).forEach(i -> {
            var parameter = funDef.parameters.get(i);
            var alloca = LLVMBuildAlloca(builder, LLVMInt32TypeInContext(context), parameter.name);
            LLVMBuildStore(builder, LLVMGetParam(function, i), alloca);
            NamedValues.put(parameter.name, alloca);
        });

        funDef.body.accept(this);
        var returnedValue = IRNodes.valueFor(funDef.body).get();
        LLVMBuildRet(builder, returnedValue);

        if (LLVMVerifyFunction(function, LLVMPrintMessageAction) > 0)
            Report.error("Napačno zgrajena funkcija " + funDef.name + "!");
    }

    @Override
    public void visit(TypeDef typeDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(VarDef varDef) {
        NamedValues.put(varDef.name, LLVMBuildAlloca(builder, LLVMInt32TypeInContext(context), varDef.name));
    }

    @Override
    public void visit(Parameter parameter) {
    }

    @Override
    public void visit(Array array) {
        // TODO Auto-generated method stub
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
