package splprime.ast.exceptions;

import splprime.scan.Token;

public class UnexpectedTokenException extends Exception {
    private Token tok;

    public UnexpectedTokenException(Token token) {
        this.tok = token;
        super("Got unexpected token of type: " + token.toString());
    }
}
