/**
 * @Author: turk
 * @Description: Sintaksni analizator.
 */

package compiler.parser;

import static compiler.lexer.TokenType.*;
import static common.RequireNonNull.requireNonNull;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import common.Report;
import compiler.lexer.Position;
import compiler.lexer.Symbol;
import compiler.lexer.TokenType;

public class Parser {
    /**
     * Seznam leksikalnih simbolov.
     */
    private final List<Symbol> symbols;
    Iterator<Symbol> symbolsIterator;

    /**
     * Ciljni tok, kamor izpisujemo produkcije. Če produkcij ne želimo izpisovati,
     * vrednost opcijske spremenljivke nastavimo na Optional.empty().
     */
    private final Optional<PrintStream> productionsOutputStream;

    public Parser(List<Symbol> symbols, Optional<PrintStream> productionsOutputStream) {
        requireNonNull(symbols, productionsOutputStream);
        this.symbols = symbols;
        this.productionsOutputStream = productionsOutputStream;
        Iterator<Symbol> symbolsIterator = symbols.iterator();
    }

    /**
     * Izvedi sintaksno analizo.
     */
    public void parse() {
        parseSource();
    }

    private void parseSource() {
        // TODO: - rekurzivno spuščanje
    }

    private void parseDefinition() {
        if (getNextToken().equals(KW_TYP)) {
            dump("definition -> type_definition");
        }
    }

    private void parseDefinitions() {
        dump("definitions -> definition definitions2");
        parseDefinition();
    }

    private TokenType getNextToken() {
        return symbolsIterator.next().getTokenType();
    }

    /**
     * Izpiše produkcijo na izhodni tok.
     */
    private void dump(String production) {
        if (productionsOutputStream.isPresent()) {
            productionsOutputStream.get().println(production);
        }
    }
}
