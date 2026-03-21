package splprime.ast.statements;

import splprime.ast.expressions.Expression;
import splprime.visitor.StatementVisitor;

public class WhileStatement extends Statement {
    public Expression condition;
    public Statement doStatement;

    public WhileStatement(Expression condition, Statement doStatement) {
        this.condition = condition;
        this.doStatement = doStatement;
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

}
