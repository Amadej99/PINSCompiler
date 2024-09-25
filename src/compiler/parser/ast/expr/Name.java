/**
 * @Author: turk
 * @Description: Ime spremenljivke.
 */

package compiler.parser.ast.expr;

import static common.RequireNonNull.requireNonNull;
import static org.bytedeco.llvm.global.LLVM.*;

import compiler.common.Visitor;
import compiler.lexer.Position;
import compiler.parser.ast.def.FunDef;
import compiler.seman.name.env.SymbolTable;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import java.util.HashMap;

public class Name extends Expr {
    /**
     * Ime spremenljivke.
     */
    public final String name;

    public Name(Position position, String name) {
        super(position);
        requireNonNull(name);
        this.name = name;
    }

    public LLVMValueRef getValueAddress(LLVMBuilderRef builder, LLVMContextRef context, SymbolTable symbolTable){
        var triple = symbolTable.definitionFor(this.name).get();
        var nameScope = triple.getScope();
        var currentScope = symbolTable.getCurrentScope();

        var diff = Math.abs(currentScope - nameScope);

        if(nameScope == 0 || diff == 0)
            return triple.getValueRef().get();

        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);
        var currentFunDef = symbolTable.definitionFor(currentFunction).get().getDef().asFunDef().get();
        var closureInstance = LLVMGetLastParam(currentFunction);

        currentFunDef = currentFunDef.parentFunction.get();

        while(diff > 1){
            closureInstance = LLVMBuildStructGEP2(builder, currentFunDef.closureType.get(), closureInstance, currentFunDef.closureSize.get() - 1, currentFunDef.name + "_closure");
            currentFunDef = currentFunDef.parentFunction.get();
            closureInstance = LLVMBuildLoad2(builder, LLVMPointerTypeInContext(context, 0), closureInstance, currentFunDef.name + "_closure");
            diff--;
        }

        var varsList = currentFunDef.capturedVariables;
        var index = varsList.indexOf(this.name);
        if(index != -1) {
            var fieldPtr = LLVMBuildStructGEP2(builder, currentFunDef.closureType.get(), closureInstance, index, currentFunDef.name + " closure_field_" + index);
            return LLVMBuildLoad2(builder, LLVMPointerType(LLVMInt32Type(), 0), fieldPtr, this.name + "_ptr");
        }

        return null;
    }

	@Override public void accept(Visitor visitor) { visitor.visit(this); }
}
