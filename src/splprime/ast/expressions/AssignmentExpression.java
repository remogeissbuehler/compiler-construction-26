package splprime.ast.expressions;

import splprime.visitor.ExpressionVisitor;

public class AssignmentExpression extends Expression {
    public String identifier;
    public Expression value;

    public AssignmentExpression(String identifier, Expression value) {
        this.identifier = identifier;
        this.value = value;
    }

    @Override
    void accept(ExpressionVisitor v) {
        v.visit(this);
    }

}
