/**
 * @Author: turk
 * @Description: Sintaksni analizator.
 */

package compiler.parser;

import static compiler.lexer.TokenType.*;
import static common.RequireNonNull.requireNonNull;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import common.Report;
import compiler.lexer.Position;
import compiler.lexer.Symbol;

public class Parser {
    /**
     * Seznam leksikalnih simbolov.
     */
    private final List<Symbol> symbols;
    private final Iterator si;

    /**
     * Ciljni tok, kamor izpisujemo produkcije. Če produkcij ne želimo izpisovati,
     * vrednost opcijske spremenljivke nastavimo na Optional.empty().
     */
    private final Optional<PrintStream> productionsOutputStream;

    public Parser(List<Symbol> symbols, Optional<PrintStream> productionsOutputStream) {
        requireNonNull(symbols, productionsOutputStream);
        this.symbols = symbols;
        this.productionsOutputStream = productionsOutputStream;
        this.si = new Iterator(symbols);
    }

    /**
     * Izvedi sintaksno analizo.
     */
    public void parse() {
        parseSource();
    }

    private void parseSource() {
        dump("source -> definitions");
        parseDefinitions();
    }

    void parseDefinitions() {
        dump("definitions -> definition definitions2");
        parseDefinition();
        parseDefinitions2();
    }

    void parseDefinition() {
        if (si.getNext().equals(KW_TYP)) {
            dump("definition -> type_definition");
            si.skip();
            parseTypeDefinition();
        } else if (si.getNext().equals(KW_VAR)) {
            dump("definition -> var_definition");
            si.skip();
            parseVarDefinition();
        } else if (si.getNext().equals(KW_FUN)) {
            dump("definition -> fun_definition");
            si.skip();
            parseFunDefinition();
        } else if (!si.getNext().equals(EOF)) {
            Report.error("Napaka v parseDefinition z " + si.getSymbol());
        }

    }

    void parseDefinitions2() {
        if (si.getNext().equals(OP_SEMICOLON)) {
            dump("definitions2 → ; definitions");
            si.skip();
            parseDefinitions();
        } else {
            dump("definitions2 → e");
        }
    }

    void parseTypeDefinition() {
        if (si.getNext().equals(IDENTIFIER)) {
            si.skip();
            if (si.getNext().equals(OP_COLON)) {
                dump("type_definition → typ identifier : type");
                si.skip();
                parseType();
            } else {
                new Error();
            }
        } else {
            new Error();
        }
    }

    void parseType() {
        if (si.getNext().equals(IDENTIFIER)) {
            dump("type → identifier");
            si.skip();
        } else if (si.getNext().equals(AT_LOGICAL)) {
            dump("type → logical");
            si.skip();
        } else if (si.getNext().equals(AT_INTEGER)) {
            dump("type → integer");
            si.skip();
        } else if (si.getNext().equals(AT_STRING)) {
            dump("type → string");
            si.skip();
        } else if (si.getNext().equals(KW_ARR)) {
            dump("type → arr [ int_const ] type");
            si.skip();
            parseArray();
        } else {
            new Error();
        }
    }

    void parseArray() {
        if (si.getNext().equals(OP_LBRACKET)) {
            si.skip();
            if (si.getNext().equals(C_INTEGER)) {
                si.skip();
                if (si.getNext().equals(OP_RBRACKET)) {
                    si.skip();
                    parseType();
                } else {
                    new Error();
                }
            } else {
                new Error();
            }
        } else {
            new Error();
        }
    }

    void parseVarDefinition() {
        dump("variable_definition → var identifier : type");
    }

    void parseFunDefinition() {
        dump("function_definition → fun identifier ( parameters ) : type = expression");

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
