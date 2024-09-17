/**
 * @Author: turk
 * @Description: Lokalne definicije.
 */

package compiler.parser.ast.expr;

import static common.RequireNonNull.requireNonNull;

import compiler.common.Visitor;
import compiler.lexer.Position;
import compiler.parser.ast.def.Defs;
import compiler.parser.ast.def.FunDef;
import compiler.parser.ast.def.VarDef;

import java.util.List;
import java.util.stream.Collectors;

public class Where extends Expr {
    /**
     * Izraz, ki se izvede v gnezdenem območju.
     */
    public final Expr expr;

    /**
     * Definicije.
     */
    public final Defs defs;

    public Where(Position position, Expr expr, Defs defs) {
        super(position);
        requireNonNull(expr);
        requireNonNull(defs);
        this.expr = expr;
        this.defs = defs;
    }

	@Override public void accept(Visitor visitor) { visitor.visit(this); }

    public boolean hasFunctionDefs() {
        return this.defs.definitions.stream().anyMatch(definition -> definition instanceof FunDef);
    }

    public List<VarDef> getVarDefs(){
        return this.defs.definitions.stream().filter(def -> def instanceof VarDef).map(def -> (VarDef) def).toList();
    }
}
