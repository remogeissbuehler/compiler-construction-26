package splprime.ast.statements;

import java.util.Optional;

import splprime.ast.expressions.Expression;
import splprime.visitor.StatementVisitor;

public class IfStatement extends Statement {
    public Expression condition;
    public Statement thenStatement;
    public Optional<Statement> elseStatement;

    public IfStatement(Expression condition, Statement then) {
        this.condition = condition;
        this.thenStatement = then;
        this.elseStatement = Optional.empty();
    }

    public IfStatement(Expression condition, Statement then, Statement els) {
        this.condition = condition;
        this.thenStatement = then;
        this.elseStatement = Optional.of(els);
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

}
