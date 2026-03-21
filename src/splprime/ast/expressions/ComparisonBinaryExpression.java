package splprime.ast.expressions;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.scan.Token;
import splprime.scan.TokenType;
import splprime.visitor.ExpressionVisitor;

public class ComparisonBinaryExpression extends AbstractBinaryExpression {
    public ComparisonBinaryExpression(Expression left, Token operator, Expression right)
            throws UnexpectedTokenException {
        super(left, operator, right);
    }

    @Override
    protected boolean checkTokenType(TokenType tt) {
        boolean valid = false;
        valid = valid || tt == TokenType.OP_EQUAL;
        valid = valid || tt == TokenType.OP_NOT_EQUAL;
        valid = valid || tt == TokenType.OP_GREATER_THAN;
        valid = valid || tt == TokenType.OP_LESS_THAN;
        valid = valid || tt == TokenType.OP_GEQ;
        valid = valid || tt == TokenType.OP_LEQ;

        return valid;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }
}
