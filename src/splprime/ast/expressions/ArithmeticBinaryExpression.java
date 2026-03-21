package splprime.ast.expressions;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.scan.Token;
import splprime.scan.TokenType;
import splprime.visitor.ExpressionVisitor;

public class ArithmeticBinaryExpression extends AbstractBinaryExpression {

    public ArithmeticBinaryExpression(Expression left, Token operator, Expression right)
            throws UnexpectedTokenException {
        super(left, operator, right);
    }

    @Override
    protected boolean checkTokenType(TokenType tt) {
        boolean valid = false;
        valid = valid || tt == TokenType.OP_PLUS;
        valid = valid || tt == TokenType.OP_MINUS;
        valid = valid || tt == TokenType.OP_STAR;
        valid = valid || tt == TokenType.OP_SLASH;

        return valid;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }

}
