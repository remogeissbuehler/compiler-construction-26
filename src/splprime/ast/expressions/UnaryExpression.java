package splprime.ast.expressions;

import splprime.scan.Token;
import splprime.visitor.ExpressionVisitor;

public class UnaryExpression extends Expression {

    public Token operator;
    public Expression expr;

    public UnaryExpression(Token operator, Expression expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }

}
