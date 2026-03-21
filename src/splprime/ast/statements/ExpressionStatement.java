package splprime.ast.statements;

import splprime.ast.expressions.Expression;

import splprime.visitor.StatementVisitor;

public class ExpressionStatement extends Statement {
    public Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

}
