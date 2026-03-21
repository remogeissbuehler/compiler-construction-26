package splprime.ast.statements;

import splprime.visitor.StatementVisitor;

public abstract class Statement {
    public abstract void accept(StatementVisitor v);
}
