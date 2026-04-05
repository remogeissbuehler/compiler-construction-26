package splprime.ast.statements;

import splprime.ast.expressions.Expression;
import splprime.scan.Token;

import java.util.Optional;

import splprime.visitor.StatementVisitor;

public class VariableDeclaration extends Statement {

    public Token iden;
    public Optional<Expression> initializer;

    public VariableDeclaration(Token iden) {
        this.iden = iden;
        this.initializer = Optional.empty();
    }

    public VariableDeclaration(Token iden, Expression initializer) {
        this.iden = iden;
        this.initializer = Optional.of(initializer);
    }

    @Override
    public void accept(StatementVisitor v) {
        v.visit(this);
    }

    public String name() {
        return this.iden.lexeme;
    }

}
