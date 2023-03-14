package compiler.parser;

import java.util.List;

import compiler.lexer.Symbol;
import compiler.lexer.TokenType;

public class Iterator {
    private int index;
    private List<Symbol> symbols;

    public Iterator(List<Symbol> symbols) {
        this.symbols = symbols;
        this.index = 0;
    }

    public Symbol getSymbol() {
        return symbols.get(index);
    }

    public TokenType getNext() {
        return symbols.get(index).tokenType;
    }

    public TokenType getPrevious() {
        return symbols.get(index--).tokenType;
    }

    public void skip() {
        // System.out.println("Skipping " + getSymbol());
        index++;
    }

    public boolean hasNext() {
        return getNext() != null;
    }
}
