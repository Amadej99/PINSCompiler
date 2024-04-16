/**
 * @ Author: turk
 * @ Description:
 */

package compiler.seman.type.type;

import static common.RequireNonNull.requireNonNull;
import static org.bytedeco.llvm.global.LLVM.LLVMArrayType2;
import static org.bytedeco.llvm.global.LLVM.LLVMInt1TypeInContext;
import static org.bytedeco.llvm.global.LLVM.LLVMInt32TypeInContext;
import static org.bytedeco.llvm.global.LLVM.LLVMPointerTypeInContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;

import common.Constants;
import common.Report;

public abstract class Type {
    /**
     * Vrne velikost tipa v bytih.
     */
    public abstract int sizeInBytes();

    /**
     * Vrne velikost tipa, če je le-ta uporabljen kot parameter/argument.
     * 
     * V primeru prenosa po referenci, je velikost tipa enaka
     * velikosti kazalca.
     */
    public abstract int sizeInBytesAsParam();

    /**
     * Ali tip strukturno enak drugemu tipu.
     */
    public abstract boolean equals(Type t);

    // ------------------------------------

    /**
     * Preveri, ali je tip atomaren tip.
     */
    public boolean isAtom() {
        return this instanceof Atom;
    }

    /**
     * Preveri, ali je tip `LOG`.
     */
    public boolean isLog() {
        return (this instanceof Atom a) && a.kind == Atom.Kind.LOG;
    }

    /**
     * Preveri, ali je tip `STR`.
     */
    public boolean isStr() {
        return (this instanceof Atom a) && a.kind == Atom.Kind.STR;
    }

    /**
     * Preveri, ali je tip `INT`.
     */
    public boolean isInt() {
        return (this instanceof Atom a) && a.kind == Atom.Kind.INT;
    }

    /**
     * Če je tip atomaren tip, ga vrne. Sicer
     * vrne `Optional.empty()`.
     */
    public Optional<Atom> asAtom() {
        if (this instanceof Atom t)
            return Optional.of(t);
        return Optional.empty();
    }

    /**
     * Preveri, ali je tip tabela.
     */
    public boolean isArray() {
        return this instanceof Array;
    }

    /**
     * Če je tip tabela, jo vrne. Sicer
     * vrne `Optional.empty()`.
     */
    public Optional<Array> asArray() {
        if (this instanceof Array t)
            return Optional.of(t);
        return Optional.empty();
    }

    /**
     * Vrne tip seznama v LLVM obliki.
     * @param type
     * @param atomType
     * @return
     */
    public static LLVMTypeRef resolveLLVMArrayType(Type type, LLVMTypeRef atomType) {
        if (!type.asArray().get().type.isArray()) {
            return LLVMArrayType2(atomType, type.asArray().get().size);
        } else
            return LLVMArrayType2(resolveLLVMArrayType(type.asArray().get().type.asArray().get(), atomType),
                    type.asArray().get().size);
    }

    /**
     * Vrne atomarni tip seznama v LLVM obliki.
     * @param context
     * @param type
     * @return
     */
    public static LLVMTypeRef resolveArrayLLVMAtomType(LLVMContextRef context, Type type) {
        while (!type.isAtom()) {
            if (!type.isArray())
                Report.error("Tip ni seznam, napaka v pomozni funkciji resolveArrayAtomType!");
            type = type.asArray().get().type;
        }
        return type.convertToLLVMType(context);
    }

    /**
     * Vrne notranji tip seznama v LLVM obliki za pravilno inicializacijo globalnih seznamov.
     * @param type
     * @param atomType
     * @return
     */
    public static LLVMTypeRef resolveInnerLLVMArrayType(Type type, LLVMTypeRef atomType) {
        if (!type.asArray().get().type.isArray()) {
            return atomType;
        } else
            return LLVMArrayType2(resolveInnerLLVMArrayType(type.asArray().get().type.asArray().get(), atomType),
                    type.asArray().get().type.asArray().get().size);
    }

    /**
     * Vrne atomarni tip seznama.
     * @param type
     * @return
     */
    public static Type resolveArrayAtomType(Type type) {
        while (!type.isAtom()) {
            if (!type.isArray())
                Report.error("Tip ni seznam, napaka v pomozni funkciji resolveArrayAtomType!");
            type = type.asArray().get().type;
        }
        return type;
    }

    /**
     * Preveri, ali je tip funkcijski tip.
     */
    public boolean isFunction() {
        return this instanceof Function;
    }

    /**
     * Če je tip funkcijski tip, ga vrne. Sicer
     * vrne `Optional.empty()`.
     */
    public Optional<Function> asFunction() {
        if (this instanceof Function t)
            return Optional.of(t);
        return Optional.empty();
    }

    /**
     * Pretvori atomarni tip v LLVM atomarni tip.
     * @param context
     * @return
     */
    public LLVMTypeRef convertToLLVMType(LLVMContextRef context) {
        if (this.isInt())
            return LLVMInt32TypeInContext(context);
        else if (this.isLog())
            return LLVMInt1TypeInContext(context);
        else if (this.isStr())
            return LLVMPointerTypeInContext(context, 0);
        else
            return null;
    }

    // ------------------------------------

    // Razredi, ki predstavljajo različne podatkovne tipe.

    /**
     * Atomarni podatkovni tip.
     */
    public static class Atom extends Type {
        /**
         * Vrsta podatkovnega tipa.
         */
        public final Kind kind;

        public Atom(Kind kind) {
            requireNonNull(kind);
            this.kind = kind;
        }

        @Override
        public int sizeInBytes() {
            return this.kind.size;
        }

        @Override
        public int sizeInBytesAsParam() {
            return this.kind.size;
        }

        @Override
        public boolean equals(Type t) {
            if (t.isAtom()) {
                if (t.asAtom().isPresent()) {
                    return t.asAtom().get().kind == this.kind;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return switch (kind) {
                case INT:
                    yield "int";
                case STR:
                    yield "str";
                case LOG:
                    yield "log";
                case VOID:
                    yield "void";
            };
        }

        public static enum Kind {
            LOG(Constants.WordSize), INT(Constants.WordSize), STR(Constants.WordSize), VOID(0);

            final int size;

            Kind(int size) {
                this.size = size;
            }
        }
    }

    /**
     * Podatkovni tip, ki predstavlja tabelo.
     */
    public static class Array extends Type {
        /**
         * Velikost (št. elementov) tabele.
         */
        public final int size;

        /**
         * Podatkovni tip elementov.
         */
        public final Type type;

        public Array(int size, Type type) {
            requireNonNull(type);
            this.size = size;
            this.type = type;
        }

        @Override
        public int sizeInBytes() {
            return this.type.sizeInBytes() * this.size;
        }

        @Override
        public int sizeInBytesAsParam() {
            return Constants.WordSize;
        }

        public int elementSizeInBytes() {
            return type.sizeInBytes();
        }

        @Override
        public boolean equals(Type t) {
            if (t.isArray()) {
                if (t.asArray().isPresent()) {
                    return t.asArray().get().size == this.size && t.asArray().get().type.equals(this.type);
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "ARR(" + size + "," + type.toString() + ")";
        }
    }

    /**
     * Podatkovni tip, ki predstavlja funkcijo.
     */
    public static class Function extends Type {
        /**
         * Tipi parametrov.
         */
        public final List<Type> parameters;

        /**
         * Tip, ki ga funkcija vrača.
         */
        public final Type returnType;

        public final boolean isVarArg;

        public Function(List<Type> parameters, Type returnType, boolean isVarArg) {
            requireNonNull(parameters);
            requireNonNull(returnType);
            this.parameters = parameters;
            this.returnType = returnType;
            this.isVarArg = isVarArg;
        }

        @Override
        public int sizeInBytes() {
            // Ni treba implementirat zares
            return this.parameters.stream().mapToInt(Type::sizeInBytes).sum();
        }

        @Override
        public int sizeInBytesAsParam() {
            // Ni treba implementirat zares
            return Constants.WordSize;
        }

        @Override
        public boolean equals(Type t) {
            if (t.isFunction()) {
                if (t.asFunction().isPresent()) {
                    return this.returnType.equals(t.asFunction().get().returnType);
                }
            }
            return false;
        }

        @Override
        public String toString() {
            var params = parameters.stream()
                    .map(t -> t.toString())
                    .collect(Collectors.joining(", "));
            return "(" + params + ") -> " + returnType.toString();
        }
    }
}
