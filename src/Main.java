
/**
 * @Author: turk
 * @Description: Vhodna točka prevajalnika.
 */

import cli.PINS;
import compiler.lexer.pinsLexer;
import compiler.parser.pinsAstVisitor;
import compiler.parser.pinsParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {

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
        // var sourceCode = Files.readString(Paths.get(cli.sourceFile));
        CharStream stream = CharStreams.fromFileName(cli.sourceFile);
        run(cli, stream);
    }

    private static void run(PINS cli, CharStream sourceCode) throws IOException {
        /**
         * Izvedi leksikalno analizo.
         */

        // var symbols = new Lexer(sourceCode).scan();

        var lexer = new pinsLexer(sourceCode);
        var tokens = new CommonTokenStream(lexer);
        var parser = new pinsParser(tokens);

        var visitor = new pinsAstVisitor();
        visitor.visit(parser.program());

        

    //     if (cli.dumpPhases.contains(Phase.LEX)) {
    //         for (var symbol : symbols) {
    //             System.out.println(symbol.toString());
    //         }
    //     }
    //     if (cli.execPhase == Phase.LEX) {
    //         return;
    //     }
    //     /**
    //      * Izvedi sintaksno analizo.
    //      */
    //     Optional<PrintStream> out = cli.dumpPhases.contains(Phase.SYN)
    //             ? Optional.of(System.out)
    //             : Optional.empty();
    //     var parser = new Parser(symbols, out);
    //     var ast = parser.parse();
    //     if (cli.execPhase == Phase.SYN) {
    //         return;
    //     }

    //     /**
    //      * Abstraktna sintaksa.
    //      */
    //     var prettyPrint = new PrettyPrintVisitor4(2, System.out);
    //     if (cli.dumpPhases.contains(Phase.AST)) {
    //         ast.accept(prettyPrint);
    //     }
    //     if (cli.execPhase == Phase.AST) {
    //         return;
    //     }
    //     /**
    //      * Izvedi razreševanje imen.
    //      */
    //     SymbolTable symbolTable = new FastSymbolTable();
    //     var definitions = new NodeDescription<Def>();
    //     var nameChecker = new NameChecker(definitions, symbolTable);
    //     ast.accept(nameChecker);
    //     if (cli.dumpPhases.contains(Phase.NAME)) {
    //         prettyPrint.definitions = Optional.of(definitions);
    //         ast.accept(prettyPrint);
    //     }
    //     if (cli.execPhase == Phase.NAME) {
    //         return;
    //     }
    //     /**
    //      * Izvedi preverjanje tipov.
    //      */
    //     var types = new NodeDescription<Type>();
    //     var typeChecker = new TypeChecker(definitions, types);
    //     ast.accept(typeChecker);
    //     if (cli.dumpPhases.contains(Phase.TYP)) {
    //         prettyPrint.definitions = Optional.of(definitions);
    //         prettyPrint.types = Optional.of(types);
    //         ast.accept(prettyPrint);
    //     }
    //     if (cli.execPhase == Phase.TYP) {
    //         return;
    //     }

    //     /**
    //      * Zgeneriraj vmesno kodo LLVM.
    //      */
    //     BytePointer error = new BytePointer();
    //     LLVMContextRef context = LLVMContextCreate();
    //     LLVMModuleRef module = LLVMModuleCreateWithNameInContext("PINS", context);
    //     LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);
    //     var LLVMCodeGenerator = new LLVMCodeGenerator(context, module, builder, types);
    //     ast.accept(LLVMCodeGenerator);

    //     LLVMPassManagerRef pm = LLVMCreatePassManager();
    //     LLVMRunPassManager(pm, module);

    //     if (LLVMVerifyModule(module, LLVMPrintMessageAction, error) != 0) {
    //         LLVMDisposeMessage(error);
    //         return;
    //     }

    //     if (cli.dumpPhases.contains(Phase.IMC)) {
    //         LLVMDumpModule(module);
    //     }

    //     if(cli.execPhase.equals(Phase.IMC))
    //         return;

    //     /**
    //      * Izvedi kodo z LLVM tolmačem.
    //      */
    //     LLVMExecutionEngineRef engine = new LLVMExecutionEngineRef();
    //     if (LLVMCreateInterpreterForModule(engine, module, error) != 0) {
    //         System.err.println("Failed to create LLVM interpreter: " + error.getString());
    //         LLVMDisposeMessage(error);
    //         return;
    //     }

    //     if (cli.dumpPhases.contains(Phase.INT)) {
    //         var mainArgument = LLVMCreateGenericValueOfInt(LLVMInt32TypeInContext(context), 1, 0);
    //         LLVMRunFunction(engine, LLVMGetNamedFunction(module, "main"), 1, mainArgument);
    //     }

    //     if(cli.execPhase.equals(Phase.INT))
    //         return;

    //     /**
    //      * Zapiši object file.
    //      */
    //     LLVMInitializeAllTargetInfos();
    //     LLVMInitializeAllTargets();
    //     LLVMInitializeAllTargetMCs();
    //     LLVMInitializeAllAsmParsers();
    //     LLVMInitializeAllAsmPrinters();

    //     var targetTriple = LLVMGetDefaultTargetTriple();

    //     LLVMTargetRef target = new LLVMTargetRef();
    //     if (LLVMGetTargetFromTriple(targetTriple, target, error) != 0) {
    //         System.out.println("Failed to get target: " + error.toString());
    //         LLVMDisposeMessage(error);
    //         return;
    //     }

    //     var targetMachine = LLVMCreateTargetMachine(target, targetTriple, LLVMGetHostCPUName(),
    //             LLVMGetHostCPUFeatures(),
    //             LLVMCodeGenLevelNone, 0, LLVMCodeModelDefault);
    //     LLVMCreateTargetDataLayout(targetMachine);
    //     LLVMSetTarget(module, targetTriple);

    //     if (LLVMTargetMachineEmitToFile(targetMachine, module, "object.o", 1, error) != 0) {
    //         System.err.println("Failed to emit object file!");
    //         LLVMDisposeMessage(error);
    //         return;
    //     }

    //     System.out.println("Succesfully written object.o");

    //     if (cli.dumpPhases.contains(Phase.OBJ)) {
    //         System.out.println("Target triple: " + LLVMGetDefaultTargetTriple().getString());
    //         System.out.println("Target CPU: " + LLVMGetHostCPUName().getString());
    //         System.out.println("CPU Features: " + LLVMGetHostCPUFeatures().getString());
    //     }

    }
}
