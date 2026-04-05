package splprime.ast.expressions;

import splprime.scan.Token;
import splprime.visitor.ExpressionVisitor;

public class VariableExpression extends Expression {
    public Token value;

    public VariableExpression(Token value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    }

}
