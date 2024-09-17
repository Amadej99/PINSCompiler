/**
 * @Author: turk
 * @Description: Definicija funkcije.
 */

package compiler.parser.ast.def;

import static common.RequireNonNull.requireNonNull;

import compiler.common.Visitor;
import compiler.lexer.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import compiler.parser.ast.type.Type;
import compiler.parser.ast.expr.Expr;
import compiler.parser.ast.expr.Where;
import compiler.seman.common.NodeDescription;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import static org.bytedeco.llvm.global.LLVM.*;

public class FunDef extends Def {
    /**
     * Parametri.
     */
    public Optional<Parameters> parameters;

    /**
     * Tip rezultata.
     */
    public Type type;

    /**
     * Jedro funkcije.
     */
    public Optional<Expr> body;

    /**
     * Starševska funkcija, če obstaja.
     */
    public Optional<FunDef> parentFunction;

    public Optional<LLVMTypeRef> closureType;

    public ArrayList<LLVMTypeRef> collectedVariables;

    /**
     * Je funkcija z variabilnim številom parametrov.
     */
    public boolean isVarArg;

    public FunDef(Position position, String name, Optional<Parameters> parameters, Type type, Optional<Expr> body, boolean isVarArg, Optional<FunDef> parentFunction) {
        super(position, name);
        requireNonNull(type);
        this.parameters = parameters;
        this.type = type;
        this.body = body;
        this.isVarArg = isVarArg;
        this.parentFunction = parentFunction;
    }

    public FunDef(Position position, String name){
        super(position, name);
    }

    public void setFunDef(Optional<Parameters> parameters, Type type, Optional<Expr> body, boolean isVarArg, Optional<FunDef> parentFunction){
        requireNonNull(type);
        this.parameters = parameters;
        this.type = type;
        this.body = body;
        this.isVarArg = isVarArg;
        this.parentFunction = parentFunction;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Optional<Where> getWhere(){
        if(body.isEmpty())
            return Optional.empty();
        if(body.get() instanceof Where where)
            return Optional.of(where);
        return Optional.empty();
    }

    /**
     * Preveri, ali ima funkcija vgnezdene funkcije.
     * @return true ali false
     */
    public boolean hasNestedFunctions(){
        if(this.getWhere().isPresent()){
            return this.getWhere().get().hasFunctionDefs();
        }
        return false;
    }

    /**
     * Vse parametre in lokalne spremenljivke shrani v obliki strukta in ga nastavi vozlišču.
     * @param context LLVMContextRef
     * @param types NodeDescription types
     * @return Optional.of(LLVMTypeRef closure)
     */
    public Optional<LLVMTypeRef> generateClosureStruct(LLVMContextRef context, NodeDescription<compiler.seman.type.type.Type> types){
        if(!hasNestedFunctions())
            return Optional.empty();

        var variables = collectVariableTypes(context, types);
        var closureStruct = LLVMStructCreateNamed(context, "closure_" + this.name);
        LLVMStructSetBody(closureStruct, new PointerPointer<>(variables.toArray(new Pointer[0])), variables.size(), 0);
        this.closureType = Optional.of(closureStruct);
        return this.closureType;
    }


    public ArrayList<LLVMTypeRef> collectVariableTypes(LLVMContextRef context, NodeDescription<compiler.seman.type.type.Type> types){
        var variables = new ArrayList<LLVMTypeRef>();

        this.parameters.ifPresent(parameters -> parameters.definitions.stream().forEachOrdered(def -> {
            var asParameter = def.asParameter().get();
            var type = types.valueFor(asParameter.type).get();
            variables.add(LLVMPointerTypeInContext(context, 0));
        }));

        this.body.get().asWhere().ifPresent(where ->{
            where.defs.definitions.stream().forEachOrdered(def -> {
                if(def instanceof VarDef varDef)
                    variables.add(LLVMPointerTypeInContext(context, 0));
            });
        });

        return variables;
    }

    public HashMap<String, LLVMValueRef> collectVariableValues(HashMap<String, LLVMValueRef> NamedValues){
        var variables = new HashMap<String, LLVMValueRef>();

        this.parameters.ifPresent(parameters -> parameters.definitions.stream().forEachOrdered(def -> {
            variables.put(def.name, NamedValues.get(def.name));
        }));

        this.body.get().asWhere().ifPresent(where ->{
            where.defs.definitions.stream().forEachOrdered(def -> {
                if(def instanceof VarDef varDef)
                    variables.put(varDef.name, NamedValues.get(varDef.name));
            });
        });

        return variables;
    }


    public static class Parameters extends Defs {
        public Parameters(Position position, List<Def> parameters){
            super(position, parameters);
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

    /**
     * Parameter funkcije.
     */
    public static class Parameter extends Def {
        /**
         * Tip parametra.
         */
        public final Type type;

        public Parameter(Position position, String name, Type type) {
            super(position, name);
            this.type = type;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    /**
     * Predstavlja variabilno število parametrov.
     * Služi le kot indikator, da je število parametrov v funkciji spremenljivo.
     * Se ne pojavi v končnem AST.
     */
    public static class VarArg extends Parameter {
        public VarArg(Position position, String name) {
            super(position, name, null);
        }

        @Override
        public void accept(Visitor visitor) {
            super.accept(visitor);
        }
    }
    }
}
