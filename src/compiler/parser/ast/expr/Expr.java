/**
 * @Author: turk
 * @Description: Vozlišče v abstraktnem sintaksnem drevesu,
 * ki predstavlja izraz.
 */

package compiler.parser.ast.expr;

import compiler.lexer.Position;
import compiler.parser.ast.Ast;

import java.util.Optional;

public abstract class Expr extends Ast {
    public Expr(Position position) {
        super(position);
    }

    public Optional<Where> asWhere() {
        if(this instanceof Where w)
            return Optional.of(w);
        return Optional.empty();
    }
}
