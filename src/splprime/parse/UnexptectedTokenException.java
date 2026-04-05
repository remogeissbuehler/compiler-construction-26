package splprime.parse;

import splprime.scan.Token;
import splprime.scan.TokenType;

public class UnexptectedTokenException extends SyntaxError {
    private TokenType expected;
    private TokenType[] expectedMulti;
    private Token actual;

    UnexptectedTokenException(Token actual, TokenType expected) {
        this.actual = actual;
        this.expected = expected;

        super("Got unexptected token " + actual.toString() + " exptected type " + expected.toString());
    }

    UnexptectedTokenException(Token actual, TokenType... expected) {
        this.actual = actual;
        this.expectedMulti = expected;

        super("Got unexptected token " + actual.toString() + " exptected types " + expected.toString());
    }
}
