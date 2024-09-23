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

    public LLVMValueRef getValueAddress(LLVMBuilderRef builder, SymbolTable symbolTable){
        var triple = symbolTable.definitionFor(this.name).get();
        var nameScope = triple.getScope();
        var currentScope = symbolTable.getCurrentScope();

        var diff = Math.abs(currentScope - nameScope);
        var scopeDiff = (int) Math.floor((diff + 1) / 2);

        if(nameScope == 0 || scopeDiff <= 1)
            return triple.getValueRef().get();

        var currentBlock = LLVMGetInsertBlock(builder);
        var currentFunction = LLVMGetBasicBlockParent(currentBlock);
        var currentFunDef = symbolTable.definitionFor(currentFunction).get().getDef().asFunDef().get();
        var closureInstance = LLVMGetLastParam(currentFunction);

        while(scopeDiff > 0){
            currentFunDef = currentFunDef.parentFunction.get();
            closureInstance = LLVMBuildStructGEP2(builder, currentFunDef.closureType.get(), closureInstance, (int) currentFunDef.closureType.get().limit(), "parent_closure");
            scopeDiff--;
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
