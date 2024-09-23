/**
 * @Author: turk
 * @Description: Vozlišče v abstraktnem sintaksnem drevesu,
 * ki predstavlja definicijo.
 */

package compiler.parser.ast.def;

import static common.RequireNonNull.requireNonNull;

import common.Report;
import compiler.lexer.Position;
import compiler.parser.ast.Ast;
import compiler.seman.name.env.SymbolTable;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import java.util.Optional;

public abstract class Def extends Ast {
    /**
     * Ime definicije.
     */
    public final String name;

    public Def(Position position, String name) {
        super(position);
        requireNonNull(name);
        this.name = name;
    }

    public Optional<FunDef.Parameters.Parameter> asParameter(){
        if(this instanceof FunDef.Parameters.Parameter p)
            return Optional.of(p);
        return Optional.empty();
    }

    public Optional<FunDef> asFunDef(){
        if(this instanceof FunDef fd)
            return Optional.of(fd);
        return Optional.empty();
    }

    public void addToSymbolTable(SymbolTable symbolTable, Optional<LLVMValueRef> valueRef){
        try{
            symbolTable.insert(this, valueRef);
        }
        catch (SymbolTable.DefinitionAlreadyExistsException e){
            Report.error("Definition already exists! " + this.name + this.position);
        }
    }
}
