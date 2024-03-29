/**
 * @ Author: turk
 * @ Description: Navidezni stroj (intepreter).
 */

package compiler.interpret;

import static common.RequireNonNull.requireNonNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import common.Constants;
import compiler.frm.Frame;
import compiler.gen.Memory;
import compiler.ir.chunk.Chunk.CodeChunk;
import compiler.ir.code.IRNode;
import compiler.ir.code.expr.*;
import compiler.ir.code.stmt.*;
import compiler.ir.IRPrettyPrint;

public class Interpreter {
    /**
     * Pomnilnik navideznega stroja.
     */
    private Memory memory;

    /**
     * Izhodni tok, kamor izpisujemo rezultate izvajanja programa.
     * <p>
     * V primeru, da rezultatov ne želimo izpisovati, nastavimo na
     * `Optional.empty()`.
     */
    private Optional<PrintStream> outputStream;

    /**
     * Generator naključnih števil.
     */
    private Random random;

    /**
     * Skladovni kazalec (kaže na dno sklada).
     */
    private int stackPointer;

    /**
     * Klicni kazalec (kaže na vrh aktivnega klicnega zapisa).
     */
    private int framePointer;

    public Interpreter(Memory memory, Optional<PrintStream> outputStream) {
        requireNonNull(memory, outputStream);
        this.memory = memory;
        this.outputStream = outputStream;
        this.stackPointer = memory.size - Constants.WordSize;
        this.framePointer = memory.size - Constants.WordSize;
    }

    // --------- izvajanje navideznega stroja ----------

    public void interpret(CodeChunk chunk) {
        memory.stM(framePointer + Constants.WordSize, 0); // argument v funkcijo main
        memory.stM(framePointer - chunk.frame.oldFPOffset(), framePointer); // oldFP
        internalInterpret(chunk, new HashMap<>());
    }

    private void internalInterpret(CodeChunk chunk, Map<Frame.Temp, Object> temps) {
        // Prestavimo frame in stack pointer
        framePointer = stackPointer;
        stackPointer -= chunk.frame.size();

        Object result = null;
        if (chunk.code instanceof SeqStmt seq) {
            for (int pc = 0; pc < seq.statements.size(); pc++) {
                var stmt = seq.statements.get(pc);
                result = execute(stmt, temps);
                if (result instanceof Frame.Label label) {
                    for (int q = 0; q < seq.statements.size(); q++) {
                        if (seq.statements.get(q) instanceof LabelStmt labelStmt && labelStmt.label.equals(label)) {
                            pc = q;
                            break;
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Linearize IR!");
        }

        // Stack in frame pointer prestavimo nazaj
        stackPointer = framePointer;
        framePointer = toInt(memory.ldM(framePointer - chunk.frame.oldFPOffset()));
    }

    private Object execute(IRStmt stmt, Map<Frame.Temp, Object> temps) {
        if (stmt instanceof CJumpStmt cjump) {
            return execute(cjump, temps);
        } else if (stmt instanceof ExpStmt exp) {
            return execute(exp, temps);
        } else if (stmt instanceof JumpStmt jump) {
            return execute(jump, temps);
        } else if (stmt instanceof LabelStmt label) {
            return null;
        } else if (stmt instanceof MoveStmt move) {
            return execute(move, temps);
        } else {
            throw new RuntimeException("Cannot execute this statement!");
        }
    }

    private Object execute(CJumpStmt cjump, Map<Frame.Temp, Object> temps) {
        // Preverimo pogoj, v kolikor je true se skoči na thenLabel, v kolikor je false
        // se skoči na elseLabel (ki je lahko tudi label za konec v kolikor else ni
        // definiran)
        var condition = toBool(execute(cjump.condition, temps));
        if (condition)
            return cjump.thenLabel;
        else if (cjump.elseLabel != null)
            return cjump.elseLabel;
        return null;
    }

    private Object execute(ExpStmt exp, Map<Frame.Temp, Object> temps) {
        return execute(exp.expr, temps);
    }

    private Object execute(JumpStmt jump, Map<Frame.Temp, Object> temps) {
        // Skoci na label
        return jump.label;
    }

    private Object execute(MoveStmt move, Map<Frame.Temp, Object> temps) {
        // Ce izvajamo move v temp potem se vrednost samo shrani v temp slovar
        if (move.dst instanceof TempExpr tempExpr) {
            temps.put(tempExpr.temp, execute(move.src, temps));
            return null;
        }

        // Ce shranjujemo v pomnilnik potem se vrednost shrani v pomnilnik (ne derefernciramo do konca, saj rabimo naslov v pomnilniku)
        if (move.dst instanceof MemExpr memExpr) {
            var destination = execute(memExpr.expr, temps);
            var source = execute(move.src, temps);

            memory.stM(toInt(destination), source);
            return null;
        }
        return null;
    }

    private Object execute(IRExpr expr, Map<Frame.Temp, Object> temps) {
        if (expr instanceof BinopExpr binopExpr) {
            return execute(binopExpr, temps);
        } else if (expr instanceof CallExpr callExpr) {
            return execute(callExpr, temps);
        } else if (expr instanceof ConstantExpr constantExpr) {
            return execute(constantExpr);
        } else if (expr instanceof EseqExpr eseqExpr) {
            throw new RuntimeException("Cannot execute ESEQ; linearize IRCode!");
        } else if (expr instanceof MemExpr memExpr) {
            return execute(memExpr, temps);
        } else if (expr instanceof NameExpr nameExpr) {
            return execute(nameExpr);
        } else if (expr instanceof TempExpr tempExpr) {
            return execute(tempExpr, temps);
        } else {
            throw new IllegalArgumentException("Unknown expr type");
        }
    }

    private Object execute(BinopExpr binop, Map<Frame.Temp, Object> temps) {
        // Izracunamo levo in desno stran izraza
        var left = execute(binop.lhs, temps);
        var right = execute(binop.rhs, temps);

        // Izracunamo rezultat glede na operator
        switch (binop.op) {
            case ADD -> {
                return toInt(left) + toInt(right);
            }
            case SUB -> {
                return toInt(left) - toInt(right);
            }
            case MUL -> {
                return toInt(left) * toInt(right);
            }
            case DIV -> {
                return toInt(left) / toInt(right);
            }
            case AND -> {
                return toInt(toBool(left) && toBool(right));
            }
            case OR -> {
                return toInt(toBool(left) || toBool(right));
            }
            case EQ -> {
                return toInt(toInt(left) == toInt(right));
            }
            case NEQ -> {
                return toInt(toInt(left) != toInt(right));
            }
            case LT -> {
                return toInt(toInt(left) < toInt(right));
            }
            case GT -> {
                return toInt(toInt(left) > toInt(right));
            }
            case GEQ -> {
                return toInt(toInt(left) >= toInt(right));
            }
            case LEQ -> {
                return toInt(toInt(left) <= toInt(right));
            }
            case MOD -> {
                return toInt(left) % toInt(right);
            }
        }
        throw new RuntimeException("Unknown binop!");
    }

    private Object execute(CallExpr call, Map<Frame.Temp, Object> temps) {
        // Preverimo ce je klic funkcije standardne knjiznice
        if (call.label.name.equals(Constants.printIntLabel)) {
            if (call.args.size() != 2) {
                throw new RuntimeException("Invalid argument count!");
            }
            var arg = execute(call.args.get(1), temps);
            outputStream.ifPresent(stream -> stream.println(arg));
            return null;
        } else if (call.label.name.equals(Constants.printStringLabel)) {
            if (call.args.size() != 2) {
                throw new RuntimeException("Invalid argument count!");
            }
            var address = execute(call.args.get(1), temps);
            var res = memory.ldM(toInt(address));
            outputStream.ifPresent(stream -> stream.println("\"" + res + "\""));
            return null;
        } else if (call.label.name.equals(Constants.printLogLabel)) {
            if (call.args.size() != 2) {
                throw new RuntimeException("Invalid argument count!");
            }
            var arg = execute(call.args.get(1), temps);
            outputStream.ifPresent(stream -> stream.println(toBool(arg)));
            return null;
        } else if (call.label.name.equals(Constants.randIntLabel)) {
            if (call.args.size() != 3) {
                throw new RuntimeException("Invalid argument count!");
            }
            var min = toInt(execute(call.args.get(1), temps));
            var max = toInt(execute(call.args.get(2), temps));
            return random.nextInt(min, max);
        } else if (call.label.name.equals(Constants.seedLabel)) {
            if (call.args.size() != 2) {
                throw new RuntimeException("Invalid argument count!");
            }
            var seed = toInt(execute(call.args.get(1), temps));
            random = new Random(seed);
            return null;
        } else if (memory.ldM(call.label) instanceof CodeChunk chunk) {
            // Ce ni standardna knjiznica, potem je to klic funkcije
            // Izracunamo vrednosti argumentov in jih shranimo v argumente klicnega zapisa z
            // njihovim odmikom, prvi je na odmiku 0 in je vedno static link
            int offset = 0;
            for (var arg : call.args) {
                var value = execute(arg, temps);
                memory.stM(stackPointer + offset, value);
                offset += 4;
            }

            // Izvedemo klicano funkcijo
            internalInterpret(chunk, new HashMap<>());
            // Rezultat funkcije preberemo s stack pointerja - prepisal je argumente
            var result = memory.ldM(stackPointer);
            return result;
        } else {
            throw new RuntimeException("Only functions can be called!");
        }
    }

    private Object execute(ConstantExpr constant) {
        return constant.constant;
    }

    private Object execute(MemExpr mem, Map<Frame.Temp, Object> temps) {
        // Izracunamo naslov in vrnemo vrednost na njem
        var address = execute(mem.expr, temps);
        return memory.ldM(toInt(address));
    }

    private Object execute(NameExpr name) {
        // Preverimo ce je to FP ali SP, sicer vrnemo naslov s to labelo
        if (name.label.name.equals("{FP}"))
            return framePointer;
        if (name.label.name.equals("{SP}"))
            return stackPointer;
        return memory.address(name.label);
    }

    private Object execute(TempExpr temp, Map<Frame.Temp, Object> temps) {
        // Dobimo vrednost iz temp slovarja
        return temps.get(temp.temp);
    }

    // ----------- pomožne funkcije -----------

    private int toInt(Object obj) {
        if (obj instanceof Integer integer) {
            return integer;
        }
        throw new IllegalArgumentException("Could not convert obj to integer!");
    }

    private boolean toBool(Object obj) {
        return toInt(obj) == 0 ? false : true;
    }

    private int toInt(boolean bool) {
        return bool ? 1 : 0;
    }

    private String prettyDescription(IRNode ir, int indent) {
        var os = new ByteArrayOutputStream();
        var ps = new PrintStream(os);
        new IRPrettyPrint(ps, indent).print(ir);
        return os.toString(Charset.defaultCharset());
    }

    private String prettyDescription(IRNode ir) {
        return prettyDescription(ir, 2);
    }

    private void prettyPrint(IRNode ir, int indent) {
        System.out.println(prettyDescription(ir, indent));
    }

    private void prettyPrint(IRNode ir) {
        System.out.println(prettyDescription(ir));
    }
}
