
/**
 * @Author: turk
 * @Description: Vhodna točka prevajalnika.
 */

import static org.bytedeco.llvm.global.LLVM.*;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMExecutionEngineRef;
import org.bytedeco.llvm.LLVM.LLVMGenericValueRef;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.LLVM.LLVMPassManagerRef;

import cli.PINS;
import cli.PINS.Phase;
import compiler.common.PrettyPrintVisitor4;
import compiler.ir.LLVMCodeGenerator;
import compiler.lexer.Lexer;
import compiler.parser.Parser;
import compiler.parser.ast.def.Def;
import compiler.seman.common.NodeDescription;
import compiler.seman.name.NameChecker;
import compiler.seman.name.env.FastSymbolTable;
import compiler.seman.name.env.SymbolTable;
import compiler.seman.type.TypeChecker;
import compiler.seman.type.type.Type;

public class Main {

    static {
        System.loadLibrary("PtrToString");
    }

    public static native String convertPointerToString(long pointer);

    /**
     * Metoda, ki izvede celotni proces prevajanja.
     *
     * @param args parametri ukazne vrstice.
     */
    public static void main(String[] args) throws Exception {
        var cli = PINS.parse(args);
        run(cli);
    }

    // -------------------------------------------------------------------

    private static void run(PINS cli) throws IOException {
        var sourceCode = Files.readString(Paths.get(cli.sourceFile));
        run(cli, sourceCode);
    }

    private static void run(PINS cli, String sourceCode) {
        /**
         * Izvedi leksikalno analizo.
         */
        var symbols = new Lexer(sourceCode).scan();
        if (cli.dumpPhases.contains(Phase.LEX)) {
            for (var symbol : symbols) {
                System.out.println(symbol.toString());
            }
        }
        if (cli.execPhase == Phase.LEX) {
            return;
        }
        /**
         * Izvedi sintaksno analizo.
         */
        Optional<PrintStream> out = cli.dumpPhases.contains(Phase.SYN)
                ? Optional.of(System.out)
                : Optional.empty();
        var parser = new Parser(symbols, out);
        var ast = parser.parse();
        if (cli.execPhase == Phase.SYN) {
            return;
        }

        /**
         * Abstraktna sintaksa.
         */
        var prettyPrint = new PrettyPrintVisitor4(2, System.out);
        if (cli.dumpPhases.contains(Phase.AST)) {
            ast.accept(prettyPrint);
        }
        if (cli.execPhase == Phase.AST) {
            return;
        }
        /**
         * Izvedi razreševanje imen.
         */
        SymbolTable symbolTable = new FastSymbolTable();
        var definitions = new NodeDescription<Def>();
        var nameChecker = new NameChecker(definitions, symbolTable);
        ast.accept(nameChecker);
        if (cli.dumpPhases.contains(Phase.NAME)) {
            prettyPrint.definitions = Optional.of(definitions);
            ast.accept(prettyPrint);
        }
        if (cli.execPhase == Phase.NAME) {
            return;
        }
        /**
         * Izvedi preverjanje tipov.
         */
        var types = new NodeDescription<Type>();
        var typeChecker = new TypeChecker(definitions, types);
        ast.accept(typeChecker);
        if (cli.dumpPhases.contains(Phase.TYP)) {
            prettyPrint.definitions = Optional.of(definitions);
            prettyPrint.types = Optional.of(types);
            ast.accept(prettyPrint);
        }
        if (cli.execPhase == Phase.TYP) {
            return;
        }

        final BytePointer error = new BytePointer();
        LLVMContextRef context = LLVMContextCreate();
        LLVMModuleRef module = LLVMModuleCreateWithNameInContext("PINS", context);
        LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);
        var LLVMCodeGenerator = new LLVMCodeGenerator(context, module, builder, types);
        ast.accept(LLVMCodeGenerator);

        LLVMPassManagerRef pm = LLVMCreatePassManager();
        LLVMRunPassManager(pm, module);

        if (LLVMVerifyModule(module, LLVMPrintMessageAction, error) != 0) {
            LLVMDisposeMessage(error);
            return;
        }

        if (cli.dumpPhases.contains(Phase.IMC)) {
            LLVMDumpModule(module);
        }
        if(cli.execPhase == Phase.IMC){
            return;
        }

        LLVMExecutionEngineRef engine = new LLVMExecutionEngineRef();
        if (LLVMCreateInterpreterForModule(engine, module, error) != 0) {
            System.err.println("Failed to create LLVM interpreter: " + error.getString());
            LLVMDisposeMessage(error);
            return;
        }

        var mainArgument = LLVMCreateGenericValueOfInt(LLVMInt32TypeInContext(context), 1, 0);
        LLVMGenericValueRef mainResult = LLVMRunFunction(engine, LLVMGetNamedFunction(module, "main"), 1, mainArgument);
        if(cli.dumpPhases.contains(Phase.INT)){
            System.out.println("Running main(1) with LLVM interpreter...");

            long returnedInteger = LLVMGenericValueToInt(mainResult, 0);
            System.out.println("Returned Integer: " + returnedInteger);
            
            // long returnedStringPointer = LLVMGenericValueToPointer(mainResult).address();
            // String returnedString = convertPointerToString(returnedStringPointer);
            // System.out.println("Returned String: " + returnedString);
        }

        // Stage 6: Dispose of the allocated resources
        LLVMDisposeExecutionEngine(engine);
        LLVMDisposePassManager(pm);
        LLVMDisposeBuilder(builder);
        LLVMContextDispose(context);
    }
}
