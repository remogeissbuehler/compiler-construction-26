package splprime.ast.statements;

import splprime.ast.expressions.Expression;
import splprime.visitor.StatementVisitor;

public class PrintStatement extends Statement {
    public Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

}
