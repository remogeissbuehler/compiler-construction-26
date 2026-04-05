package splprime.ast.expressions;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.scan.Token;
import splprime.scan.TokenType;

public abstract class AbstractBinaryExpression extends Expression {
    public Expression left;
    public Token operator;
    public Expression right;

    protected abstract boolean checkTokenType(TokenType tt);

    public AbstractBinaryExpression(Expression left, Token operator, Expression right) {
        // boolean valid = checkTokenType(operator.type);
        // if (!valid) {
        // throw new UnexpectedTokenException(operator);
        // }

        this.left = left;
        this.operator = operator;
        this.right = right;
    }

}
