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
     * 
     */
    public NodeDescription<LLVMValueRef> IRNodes;

    /**
     *
     */
    public HashMap<String, LLVMValueRef> NamedValues;

    public LLVMCodeGenerator(LLVMContextRef context, LLVMModuleRef module, LLVMBuilderRef builder, NodeDescription<Type> types) {
        this.context = context;
        this.module = module;
        this.builder = builder;
        this.types = types;
        this.IRNodes = new NodeDescription<LLVMValueRef>();
        this.NamedValues = new HashMap<String, LLVMValueRef>();
    }

    @Override
    public void visit(Call call) {
        var calledFunction = LLVMGetNamedFunction(module, call.name);

        call.arguments.forEach(argument -> argument.accept(this));

        var calledFunctionParameterSize = LLVMCountParams(calledFunction);

        var arguments = new PointerPointer<>(calledFunctionParameterSize);

        IntStream.range(0, calledFunctionParameterSize).forEach(i -> {
            IRNodes.valueFor(call.arguments.get(i)).ifPresent(value -> arguments.put(i, value));
        });

        LLVMBuildCall2(builder, LLVMTypeOf(calledFunction), calledFunction, arguments, calledFunctionParameterSize,
                call.name);
    }

    @Override
    public void visit(Binary binary) {
        binary.left.accept(this);
        binary.right.accept(this);

        var left = IRNodes.valueFor(binary.left).get();
        var right = IRNodes.valueFor(binary.right).get();

        if (binary.operator.equals(Binary.Operator.ASSIGN)) {
            LLVMBuildStore(builder, right, left);
        } else if (binary.operator.equals(Binary.Operator.ADD)) {
            LLVMBuildAdd(builder, left, right, "Add");
        }
    }

    @Override
    public void visit(Block block) {
        block.expressions.forEach(expr -> expr.accept(this));
    }

    @Override
    public void visit(For forLoop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Name name) {
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Literal literal) {
        types.valueFor(literal).ifPresent(type -> {
            if (type.isInt())
                IRNodes.store(LLVMConstInt(LLVMInt32Type(), Integer.parseInt(literal.value), 0), literal);
            else if (type.isLog())
                IRNodes.store(LLVMConstInt(LLVMInt1Type(), literal.value.equals("true") ? 1 : 0, 0), literal);
            else if (type.isStr())
                IRNodes.store(LLVMConstString(literal.value, literal.value.length(), 0), literal);
        });
    }

    @Override
    public void visit(Unary unary) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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

        LLVMTypeRef functionType = null;
        var funDefType = types.valueFor(funDef.type);
        if (funDefType.isPresent()) {
            functionType = LLVMFunctionType(LLVMInt32TypeInContext(context), parameterTypes, funDef.parameters.size(),
                    0);
        } else {
            Report.error("Napaka pri kreiranju funkcije");
        }

        var function = LLVMAddFunction(module, funDef.name, functionType);

        IRNodes.store(function, funDef);

        LLVMCreateBasicBlockInContext(context, "entry");

        NamedValues.clear();

        funDef.parameters.forEach(parameter -> parameter.accept(this));

        funDef.body.accept(this);
    }

    @Override
    public void visit(TypeDef typeDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(VarDef varDef) {
        types.valueFor(varDef.type).ifPresent(type -> {
            // IRNodes.store(LLVMBuildAlloca(builder, LLVMInt32Type(), varDef.name),
            // varDef);
            NamedValues.put(varDef.name, LLVMBuildAlloca(builder, LLVMInt32Type(), varDef.name));
        });
    }

    @Override
    public void visit(Parameter parameter) {
        NamedValues.put(parameter.name, LLVMBuildAlloca(builder, LLVMInt32TypeInContext(context), parameter.name));
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
