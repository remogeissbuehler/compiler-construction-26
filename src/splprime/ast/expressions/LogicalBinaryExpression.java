package splprime.ast.expressions;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.scan.Token;
import splprime.scan.TokenType;
import splprime.visitor.ExpressionVisitor;

public class LogicalBinaryExpression extends AbstractBinaryExpression {

    public LogicalBinaryExpression(Expression left, Token operator, Expression right) throws UnexpectedTokenException {
        super(left, operator, right);
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }

    @Override
    protected boolean checkTokenType(TokenType tt) {
        return (tt == TokenType.KEYWORD_AND) || (tt == TokenType.KEYWORD_OR);
    }

}
