/**
 * @Author: turk
 * @Description: Unarni izraz.
 */

package compiler.parser.ast.expr;

import static common.RequireNonNull.requireNonNull;

import compiler.common.Visitor;
import compiler.lexer.Position;

public class Unary extends Expr {
    /**
     * Podizraz.
     */
    public final Expr expr;

    /**
     * Operator.
     */
    public final Operator operator;

    public Unary(Position position, Expr expr, Operator operator) {
        super(position);
        requireNonNull(expr);
        requireNonNull(operator);
        this.expr = expr;
        this.operator = operator;
    }

	@Override public void accept(Visitor visitor) { visitor.visit(this); }

    public static enum Operator {
        ADD("+"), 
        SUB("-"), 
        NOT("!");

        public final String symbol;

        private Operator(String symbol) {
            this.symbol = symbol;
        }

        public static Operator fromSymbol(String symbol) {
            for (Operator op : Operator.values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            return null;
        }
    }
}
