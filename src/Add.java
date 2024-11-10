import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.*;

import static org.bytedeco.llvm.global.LLVM.*;

public class Add {

    public static void main(String[] args) {
        // Inicializiramo potrebne LLVM gradnike.
        LLVMContextRef context = LLVMContextCreate();
        LLVMModuleRef module = LLVMModuleCreateWithNameInContext("PINS_SESTEVEK", context);
        LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);

        LLVMTypeRef i32Type = LLVMInt32TypeInContext(context);
        LLVMTypeRef [] parameters = { i32Type, i32Type };
        LLVMTypeRef addType = LLVMFunctionType(i32Type, new PointerPointer(parameters), 2,0);
        LLVMValueRef add = LLVMAddFunction(module, "sestevek", addType);
        // Dobimo vrednosti parametrov
        LLVMValueRef a = LLVMGetParam(add, 0);
        LLVMValueRef b = LLVMGetParam(add, 1);
        LLVMBasicBlockRef entry = LLVMAppendBasicBlockInContext(context, add, "entry");
        LLVMPositionBuilderAtEnd(builder, entry);
        LLVMValueRef result = LLVMBuildAdd(builder, a, b, "result");

        LLVMBuildRet(builder, result);
        // Izpišemo generirano vmesno predstavitev na stderr.
        LLVMDumpModule(module);
    }
}
