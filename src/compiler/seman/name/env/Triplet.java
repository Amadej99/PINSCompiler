package compiler.seman.name.env;

import compiler.parser.ast.def.Def;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import java.util.Optional;

public class Triplet {
    final int scope;
    final Def def;
    final Optional<LLVMValueRef> valueRef;

    public Triplet(int scope, Def def, Optional<LLVMValueRef> valueRef) {
        this.scope = scope;
        this.def = def;
        this.valueRef = valueRef;
    }

    public Def getDef(){
        return this.def;
    }

    public int getScope(){
        return this.scope;
    }

    public Optional<LLVMValueRef> getValueRef() { return this.valueRef; }
}