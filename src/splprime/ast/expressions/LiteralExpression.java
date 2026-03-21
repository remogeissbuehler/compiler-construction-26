package splprime.ast.expressions;

import splprime.scan.Token;
import splprime.visitor.ExpressionVisitor;

public class LiteralExpression extends Expression {
    public Token value;

    public LiteralExpression(Token value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }

}
