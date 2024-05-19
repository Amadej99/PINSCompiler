package compiler.parser;
import org.antlr.v4.runtime.*;

import common.Report;

public class PinsErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
            sourceName = String.format("%s [%d:%d]:", sourceName, line, charPositionInLine);
        }

        Report.error(sourceName + " " + msg);
    }
}