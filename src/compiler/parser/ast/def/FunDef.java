/**
 * @Author: turk
 * @Description: Definicija funkcije.
 */

package compiler.parser.ast.def;

import static common.RequireNonNull.requireNonNull;

import compiler.common.Visitor;
import compiler.lexer.Position;

import java.util.List;
import java.util.Optional;

import compiler.parser.ast.type.Type;
import compiler.parser.ast.expr.Expr;

public class FunDef extends Def {
    /**
     * Parametri.
     */
    public final Optional<List<Parameter>> parameters;

    /**
     * Tip rezultata.
     */
    public final Type type;

    /**
     * Jedro funkcije.
     */
    public final Optional<Expr> body;

    /**
     * Je funkcija z variabilnim številom parametrov.
     */
    public final boolean isVarArg;

    public FunDef(Position position, String name, Optional<List<Parameter>> parameters, Type type, Optional<Expr> body, boolean isVarArg) {
        super(position, name);
        requireNonNull(type);
        this.parameters = parameters;
        this.type = type;
        this.body = body;
        this.isVarArg = isVarArg;
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
        public VarArg(Position position, String name, Type type) {
            super(position, name, type);
        }

        @Override
        public void accept(Visitor visitor) {
            super.accept(visitor);
        }
    }
}
