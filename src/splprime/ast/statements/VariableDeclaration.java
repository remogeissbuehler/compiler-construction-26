package splprime.ast.statements;

import splprime.ast.expressions.Expression;
import java.util.Optional;

import splprime.visitor.StatementVisitor;

public class VariableDeclaration extends Statement {

    public String name;
    public Optional<Expression> initializer;

    public VariableDeclaration(String name) {
        this.name = name;
        this.initializer = Optional.empty();
    }

    public VariableDeclaration(String name, Expression initializer) {
        this.name = name;
        this.initializer = Optional.of(initializer);
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

}
