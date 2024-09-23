package compiler.ir;

import compiler.seman.name.env.FastSymbolTable;
import compiler.seman.name.env.SymbolTable;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import common.Report;

import static org.bytedeco.llvm.global.LLVM.*;

import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.Pointer;

import compiler.common.Visitor;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.FunDef;
import compiler.parser.ast.def.FunDef.Parameters;
import compiler.parser.ast.def.FunDef.Parameters.Parameter;
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

import static compiler.seman.type.type.Type.resolveArrayAtomType;
import static compiler.seman.type.type.Type.resolveArrayLLVMAtomType;
import static compiler.seman.type.type.Type.resolveInnerLLVMArrayType;
import static compiler.seman.type.type.Type.resolveLLVMArrayType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * Razrešeni tipi.
     */
    public NodeDescription<Type> types;

    /**
     * Preslikava AST vozlišče -> LLVMValueRef
     */
    public NodeDescription<LLVMValueRef> IRNodes;

    public SymbolTable symbolTable;

    /**
     * Tipi funkcij.
     */
    public HashMap<String, LLVMTypeRef> functionTypes;

    /**
     * @param context
     * @param module
     * @param builder
     * @param types
     */
    public LLVMCodeGenerator(LLVMContextRef context, LLVMModuleRef module, LLVMBuilderRef builder, NodeDescription<Type> types) {
        this.context = context;
        this.module = module;
        this.builder = builder;
        this.types = types;
        this.IRNodes = new NodeDescription<LLVMValueRef>();
        this.functionTypes = new HashMap<>();
        this.symbolTable = new FastSymbolTable();
    }

    @Override
    public void visit(Call call) {
        var calledFunction = LLVMGetNamedFunction(module, call.name);
        var triple = symbolTable.definitionFor(call.name);
        var calledFunDef = triple.get().getDef().asFunDef().get();

        call.arguments.ifPresent(arguments -> arguments.forEach(argument -> argument.accept(this)));

        var argumentsList = new ArrayList<LLVMValueRef>();

        call.arguments.ifPresent(arguments -> {
            arguments.stream().forEachOrdered(argument -> {
                if (argument instanceof Name name && types.valueFor(name).get().isArray()) {
                    argumentsList.add(symbolTable.definitionFor(name.name).get().getValueRef().get());
                } else {
                    argumentsList.add(IRNodes.valueFor(argument).get());
                }
            });
        });

        calledFunDef.parentFunction.ifPresent(parentFunDef -> {
            argumentsList.add(parentFunDef.closureInstance.get());
        });

        IRNodes.store(LLVMBuildCall2(builder, functionTypes.get(call.name), calledFunction, new PointerPointer(argumentsList.toArray(new Pointer[0])),
                argumentsList.size(), call.name), call);
    }

    @Override
    public void visit(Binary binary) {
        if (binary.operator.equals(Binary.Operator.ASSIGN)) {
            binary.right.accept(this);
            var right = IRNodes.valueFor(binary.right).get();

            if (binary.left instanceof Name name) {
                var address = name.getValueAddress(builder, symbolTable);
                var type = types.valueFor(name).get();

                if (type.isArray()) {
                    LLVMBuildMemCpy(builder, address, 0, right, 0, LLVMConstInt(LLVMInt32TypeInContext(context), type.getSize(), 0));
                } else {
                    LLVMBuildStore(builder, right, address);
                }

                IRNodes.store(right, binary);
            } else if (binary.left instanceof Binary left) {
                var name = left.getArrayName();
                var type = types.valueFor(name).get();
                var arrayType = resolveLLVMArrayType(type, resolveArrayLLVMAtomType(context, type));

                var gep = buildGEPForArrayElement(name, arrayType, left);
                var rightType = types.valueFor(binary.right).get();

                if (rightType.isArray()) {
                    LLVMBuildMemCpy(builder, gep, 0, right, 0, LLVMConstInt(LLVMInt32TypeInContext(context), rightType.getSize(), 0));
                } else {
                    LLVMBuildStore(builder, right, gep);
                }

                IRNodes.store(right, binary);
            } else {
                Report.error("Leva stran operacije prirejanja mora biti spremenljivka!");
            }
        }

        // TODO: Review!
        if (binary.operator.equals(Binary.Operator.ARR) && binary.left instanceof Binary) {
            binary.right.accept(this);
        } else {
            binary.left.accept(this);
            binary.right.accept(this);
        }

        LLVMValueRef left = null;

        if (!(binary.operator.equals(Binary.Operator.ARR) && binary.left instanceof Binary))
            left = IRNodes.valueFor(binary.left).get();
        var right = IRNodes.valueFor(binary.right).get();

        if (binary.operator.equals(Binary.Operator.ADD)) {
            IRNodes.store(LLVMBuildAdd(builder, left, right, "add"), binary);
        } else if (binary.operator.equals(Binary.Operator.SUB)) {
            IRNodes.store(LLVMBuildSub(builder, left, right, "sub"), binary);
        } else if (binary.operator.equals(Binary.Operator.MUL)) {
            IRNodes.store(LLVMBuildMul(builder, left, right, "mul"), binary);
        } else if (binary.operator.equals(Binary.Operator.DIV)) {
            IRNodes.store(LLVMBuildUDiv(builder, left, right, "div"), binary);
        } else if (binary.operator.equals(Binary.Operator.MOD)) {
            IRNodes.store(LLVMBuildURem(builder, left, right, "mod"), binary);
        } else if (binary.operator.equals(Binary.Operator.EQ)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntEQ, left, right, "eq"), binary);
        } else if (binary.operator.equals(Binary.Operator.NEQ)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntNE, left, right, "neq"), binary);
        } else if (binary.operator.equals(Binary.Operator.LT)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSLT, left, right, "lt"), binary);
        } else if (binary.operator.equals(Binary.Operator.LEQ)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSLE, left, right, "leq"), binary);
        } else if (binary.operator.equals(Binary.Operator.GT)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSGT, left, right, "gt"), binary);
        } else if (binary.operator.equals(Binary.Operator.GEQ)) {
            IRNodes.store(LLVMBuildICmp(builder, LLVMIntSGE, left, right, "geq"), binary);
        } else if (binary.operator.equals(Binary.Operator.AND)) {
            IRNodes.store(LLVMBuildAnd(builder, left, right, "and"), binary);
        } else if (binary.operator.equals(Binary.Operator.OR)) {
            IRNodes.store(LLVMBuildOr(builder, left, right, "or"), binary);
        } else if (binary.operator.equals(Binary.Operator.ARR)) {
            var name = binary.getArrayName();
            var arrayType = types.valueFor(name).get();
            var arrayAtomType = resolveArrayLLVMAtomType(context, arrayType);
            var arrayLLVMType = resolveLLVMArrayType(arrayType, arrayAtomType);

            var gep = buildGEPForArrayElement(name, arrayLLVMType, binary);

            var indices = buildArrayIndices(binary);
            var dereferenceCount = (int) indices.limit() - 1;
            indices.deallocate();

            var loadType = arrayType.dereferenceArray(dereferenceCount, context);

            if (LLVMGetTypeKind(loadType) == LLVMArrayTypeKind || LLVMGetTypeKind(loadType) == LLVMPointerTypeKind) {
                IRNodes.store(gep, binary);
            } else {
                IRNodes.store(LLVMBuildLoad2(builder, loadType, gep, "loaded_element"), binary);
            }
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
        // Process the initial value of the loop counter
        forLoop.low.accept(this);

        // Get the current block and function
        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);

        // Create basic blocks for loop body and after loop
        var loopBlock = LLVMAppendBasicBlock(currentFunction, "loop");
        var afterBlock = LLVMAppendBasicBlock(currentFunction, "afterLoop");

        // Initialize the counter
        var counterAddress = symbolTable.definitionFor(forLoop.counter.name).get().getValueRef().get();
        LLVMBuildStore(builder, IRNodes.valueFor(forLoop.low).get(), counterAddress);

        // Jump to the loop block
        LLVMBuildBr(builder, loopBlock);

        // Start inserting into the loop block
        LLVMPositionBuilderAtEnd(builder, loopBlock);

        // Load the current value of the counter
        var counterValue = LLVMBuildLoad2(builder, LLVMInt32TypeInContext(context), counterAddress,
                forLoop.counter.name + " value");

        // Visit the body of the loop
        forLoop.body.accept(this);

        // Process the step value
        forLoop.step.accept(this);

        // Calculate the next value of the counter
        var nextCounterValue = LLVMBuildAdd(builder, counterValue, IRNodes.valueFor(forLoop.step).get(), "nextValue");

        // Store the next counter value
        LLVMBuildStore(builder, nextCounterValue, counterAddress);

        // Process the high value of the loop
        forLoop.high.accept(this);

        // Compare the counter with the high value
        var condition = LLVMBuildICmp(builder, LLVMIntULT, nextCounterValue, IRNodes.valueFor(forLoop.high).get(),
                "counter < high");

        // Create a conditional branch to either loop or afterLoop
        LLVMBuildCondBr(builder, condition, loopBlock, afterBlock);

        // Set the builder to insert into the afterBlock
        LLVMPositionBuilderAtEnd(builder, afterBlock);

        // Optionally, store some final value
        IRNodes.store(LLVMConstInt(LLVMInt32TypeInContext(context), 0, 0), forLoop);
    }

    @Override
    public void visit(Name name) {
        var alloca = name.getValueAddress(builder, symbolTable);

        types.valueFor(name).ifPresent(type -> {
            if (type.isArray()) {
                IRNodes.store(alloca, name);
            } else {
                IRNodes.store(LLVMBuildLoad2(builder, type.convertToLLVMType(context), alloca, name.name), name);
            }
        });
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

        if (ifThenElse.elseExpression.isPresent())
            LLVMBuildCondBr(builder, condition, thenBlock, elseBlock);
        else
            LLVMBuildCondBr(builder, condition, thenBlock, exitBlock);

        LLVMPositionBuilderAtEnd(builder, thenBlock);
        ifThenElse.thenExpression.accept(this);
        LLVMBuildBr(builder, exitBlock);
        thenBlock = LLVMGetInsertBlock(builder);

        if (ifThenElse.elseExpression.isPresent()) {
            LLVMAppendExistingBasicBlock(currentFunction, elseBlock);
            LLVMPositionBuilderAtEnd(builder, elseBlock);
            ifThenElse.elseExpression.ifPresent(expr -> expr.accept(this));
            LLVMBuildBr(builder, exitBlock);
            elseBlock = LLVMGetInsertBlock(builder);
        }

        LLVMAppendExistingBasicBlock(currentFunction, exitBlock);
        LLVMPositionBuilderAtEnd(builder, exitBlock);

//      TODO: You must not allow two different types of expressions in an if else statement.
        if (ifThenElse.elseExpression.isPresent()) {
            var phi = LLVMBuildPhi(builder, LLVMTypeOf(IRNodes.valueFor(ifThenElse.thenExpression).get()), "phi");
            var phiValues = new PointerPointer<>(2);
            phiValues.put(0, IRNodes.valueFor(ifThenElse.thenExpression).get());
            phiValues.put(1, IRNodes.valueFor(ifThenElse.elseExpression.get()).get());

            var phiBlocks = new PointerPointer<>(2);
            phiBlocks.put(0, thenBlock);
            phiBlocks.put(1, elseBlock);

            LLVMAddIncoming(phi, phiValues, phiBlocks, 2);

            phiBlocks.deallocate();
            phiValues.deallocate();
        }

        IRNodes.store(LLVMConstInt(LLVMInt32TypeInContext(context), 0, 0), ifThenElse);
    }

    @Override
    public void visit(Literal literal) {
        types.valueFor(literal).ifPresent(type -> {
            if (type.isInt())
                IRNodes.store(LLVMConstInt(LLVMInt32TypeInContext(context), Integer.parseInt(literal.value), 0),
                        literal);
            else if (type.isLog())
                IRNodes.store(LLVMConstInt(LLVMInt1TypeInContext(context), literal.value.equals("true") ? 1 : 0, 0),
                        literal);
            else if (type.isStr()) {
                var string = LLVMBuildGlobalStringPtr(builder, literal.value.replace("\\n", "\n"), ".string");
                var indices = new PointerPointer<>(1).put(0, LLVMConstInt(LLVMInt64TypeInContext(context), 0, 0));
                var gep = LLVMBuildInBoundsGEP2(builder, LLVMPointerTypeInContext(context, 0), string, indices, 1,
                        "str");
                IRNodes.store(gep, literal);

                indices.deallocate();
            }
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
                IRNodes.store(LLVMBuildICmp(builder, LLVMIntEQ, value,
                        LLVMConstInt(LLVMInt1TypeInContext(context), 0, 0), "!"), unary);
            }
        });
    }

    @Override
    public void visit(While whileLoop) {
        whileLoop.condition.accept(this);

        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);
        var loop = LLVMAppendBasicBlock(currentFunction, "loop");
        var exit = LLVMCreateBasicBlockInContext(context, "exit");

        LLVMBuildCondBr(builder, IRNodes.valueFor(whileLoop.condition).get(), loop, exit);
        LLVMPositionBuilderAtEnd(builder, loop);
        whileLoop.body.accept(this);
        whileLoop.condition.accept(this);
        LLVMBuildCondBr(builder, IRNodes.valueFor(whileLoop.condition).get(), loop, exit);

        LLVMAppendExistingBasicBlock(currentFunction, exit);
        LLVMPositionBuilderAtEnd(builder, exit);
        IRNodes.store(LLVMConstInt(LLVMInt32TypeInContext(context), 0, 0), whileLoop);
    }

    @Override
    public void visit(Where where) {
        where.defs.definitions.stream().filter(def -> !(def instanceof VarDef)).toList().forEach(def -> def.accept(this));
        where.expr.accept(this);
        symbolTable.popScope();
        var whereReturn = IRNodes.valueFor(where.expr).get();
        IRNodes.store(whereReturn, where);
    }

    @Override
    public void visit(Defs defs) {
        // Globalne spremenljivke je treba alocirati najprej, ne glede na to kje v kodi
        // se nahajajo.
        // Potrebno jih je alocirati le enkrat, na začetku.
        defs.definitions.forEach(def -> {
            if (def instanceof VarDef)
                def.accept(this);
            if (def instanceof FunDef funDef)
                declareFunction(funDef);
        });
        defs.definitions.forEach(def -> {
            if (!(def instanceof VarDef))
                def.accept(this);
        });
    }

    private void declareFunction(FunDef funDef) {
        var parameterTypes = new ArrayList<>();

        funDef.parameters.ifPresent(parameters -> {
            parameters.definitions.stream().forEach(parameter -> {
                var asParameter = parameter.asParameter().get();
                types.valueFor(asParameter.type).ifPresent(type -> {
                    if (type.isArray())
                        parameterTypes.add(LLVMPointerTypeInContext(context, 0));
                    else
                        parameterTypes.add(type.convertToLLVMType(context));
                });
            });
        });

        funDef.parentFunction.ifPresent(def -> parameterTypes.addLast(LLVMPointerTypeInContext(context, 0)));

        var returnType = types.valueFor(funDef.type).get();
        LLVMTypeRef LLVMReturnType = null;
        if (returnType.isArray())
            LLVMReturnType = LLVMPointerTypeInContext(context, 0);
        else
            LLVMReturnType = returnType.convertToLLVMType(context);

        var functionType = LLVMFunctionType(LLVMReturnType, new PointerPointer(parameterTypes.toArray(new Pointer[0])), parameterTypes.size(),
                funDef.isVarArg ? 1 : 0);

        functionTypes.put(funDef.name, functionType);
        LLVMAddFunction(module, funDef.name, functionType);
    }

    @Override
    public void visit(FunDef funDef) {
        var function = LLVMGetNamedFunction(module, funDef.name);

        if (function == null) {
            declareFunction(funDef);
            function = LLVMGetNamedFunction(module, funDef.name);
        }

        var closureStruct = funDef.generateClosureStruct(context);

        var oldBuildingBlock = LLVMGetInsertBlock(builder);

        funDef.addToSymbolTable(symbolTable, Optional.of(function));

        symbolTable.pushScope();
        System.out.println("Scope pushed to: " + symbolTable.getCurrentScope() + " " + funDef.name);

        var finalFunction = function;
        funDef.body.ifPresent(body -> {
            var entry = LLVMAppendBasicBlockInContext(context, finalFunction, "entry");
            LLVMPositionBuilderAtEnd(builder, entry);

            funDef.parameters.ifPresent(parameters -> {
                IntStream.range(0, parameters.definitions.size()).forEach(i -> {
                    var parameter = (Parameter) funDef.parameters.get().definitions.get(i);
                    var parameterType = types.valueFor(parameter.type).get();
                    var LLVMParameterType = parameterType.convertToLLVMType(context);

                    if (!parameterType.isArray()) {
                        var alloca = LLVMBuildAlloca(builder, LLVMParameterType, parameter.name);
                        LLVMBuildStore(builder, LLVMGetParam(finalFunction, i), alloca);
                        parameter.addToSymbolTable(symbolTable, Optional.of(alloca));
                    } else
                        parameter.addToSymbolTable(symbolTable, Optional.of(LLVMGetParam(finalFunction, i)));
                });
            });

            funDef.getWhere().ifPresent(where -> {
                symbolTable.pushScope();
                System.out.println("Scope pushed to: " + symbolTable.getCurrentScope() + " " + funDef.name + " WHERE");
                where.getVarDefs().stream().forEachOrdered(varDef -> varDef.accept(this));
            });

            var capturedVars = new ArrayList<String>();

            closureStruct.ifPresent(struct -> {
                var closureInstance = LLVMBuildAlloca(builder, struct, funDef.name + "_closure");
                funDef.closureInstance = Optional.of(closureInstance);
                var capturedVariables = funDef.collectVariableValues(symbolTable);
                var variableNames = capturedVariables.keySet().stream().toList();

                IntStream.range(0, capturedVariables.size()).forEach(i -> {
                    LLVMValueRef ptr = LLVMBuildStructGEP2(builder, struct, closureInstance, i, funDef.name + " closure_field_" + i);
                    LLVMBuildStore(builder, capturedVariables.get(variableNames.get(i)), ptr);
                    capturedVars.add(i, variableNames.get(i));
                });
            });

            funDef.capturedVariables = capturedVars;

            body.accept(this);

            var returnedValue = IRNodes.valueFor(body).get();
            LLVMBuildRet(builder, returnedValue);

            if (LLVMVerifyFunction(finalFunction, LLVMPrintMessageAction) > 0)
                Report.error("Napačno zgrajena funkcija " + funDef.name + "!");

            if(funDef.parentFunction.isPresent())
                LLVMPositionBuilderAtEnd(builder, oldBuildingBlock);
        });

        symbolTable.popScope();
        System.out.println("Scope popped to: " + symbolTable.getCurrentScope() + " " + funDef.name);
    }

    @Override
    public void visit(TypeDef typeDef) {
    }

    @Override
    public void visit(VarDef varDef) {
        var currentBlock = LLVMGetInsertBlock(builder);

        types.valueFor(varDef).ifPresent(type -> {
            LLVMValueRef alloca = null;
            if (type.isArray()) {
                var arrayLLVMAtomType = resolveArrayLLVMAtomType(context, type);
                var outerLLVMArrayType = resolveLLVMArrayType(type, arrayLLVMAtomType);

                if (currentBlock == null) {
                    var asArray = type.asArray().get();
                    var arraySize = asArray.size;
                    var LLVMArrayType = resolveInnerLLVMArrayType(type, arrayLLVMAtomType);
                    var arrayAtomType = resolveArrayAtomType(type);
                    alloca = LLVMAddGlobal(module, outerLLVMArrayType, varDef.name);
                    var zeroes = new PointerPointer<>(1);
                    for (int i = 0; i < arraySize; i++) {
                        if (arrayAtomType.isInt() || arrayAtomType.isLog())
                            zeroes.put(i, LLVMConstInt(arrayLLVMAtomType, 0, 0));
                        else if (arrayAtomType.isStr())
                            zeroes.put(i, LLVMConstNull(LLVMPointerTypeInContext(context, 0)));
                    }

                    LLVMSetInitializer(alloca, LLVMConstArray2(LLVMArrayType, zeroes, arraySize));
                    zeroes.deallocate();
                } else {
                    alloca = LLVMBuildAlloca(builder, outerLLVMArrayType,
                            varDef.name);
                }
            } else {
                var llvmType = type.convertToLLVMType(context);
                if (currentBlock == null) {
                    alloca = LLVMAddGlobal(module, llvmType, varDef.name);
                    LLVMSetInitializer(alloca, getGlobalInitializer(type));
                } else
                    alloca = LLVMBuildAlloca(builder, llvmType, varDef.name);

            }
            varDef.addToSymbolTable(symbolTable, Optional.of(alloca));
        });
    }

    @Override
    public void visit(Parameters parameters) {
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

    private LLVMValueRef buildGEPForArrayElement(Name name, LLVMTypeRef arrayType, Binary left) {
        var indices = buildArrayIndices(left);
        var basePointer = name.getValueAddress(builder, symbolTable);

        var gep = LLVMBuildInBoundsGEP2(builder, arrayType, basePointer, indices,
                (int) indices.limit(), name.name);

        indices.deallocate();
        return gep;
    }

    private PointerPointer<Pointer> buildArrayIndices(Binary arrayAccess) {
        var indicesAsList = new ArrayList<Pointer>();
        indicesAsList.add(LLVMConstInt(LLVMInt64TypeInContext(context), 0, 0));
        resolveArrayIndeces(arrayAccess, indicesAsList);

        return new PointerPointer<>(indicesAsList.toArray(new Pointer[0]));
    }

    private void resolveArrayIndeces(Binary binary, List<Pointer> indices) {
        binary.right.accept(this);
        var currentIndex = IRNodes.valueFor(binary.right).get();

        if (!(binary.left instanceof Name)) {
            resolveArrayIndeces((Binary) binary.left, indices);
            indices.add(currentIndex);
        } else {
            indices.add(currentIndex);
        }
    }

    private LLVMValueRef getGlobalInitializer(Type type) {
        if (type.isInt() || type.isLog())
            return LLVMConstInt(type.convertToLLVMType(context), 0, 0);
        else
            return LLVMConstNull(type.convertToLLVMType(context));
    }

}
