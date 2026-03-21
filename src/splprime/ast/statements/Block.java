package splprime.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;

import splprime.visitor.StatementVisitor;

public class Block extends Statement {

    private final int DEFAULT_CAPACITY = 8;
    ArrayList<Statement> children;

    public Block() {
        this.children = new ArrayList<>(DEFAULT_CAPACITY);
    }

    public Block(Statement... children) {
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    public void add(Statement statement) {
        this.children.add(statement);
    }

    public Statement[] getChildren() {
        return (Statement[]) children.toArray();
    }

    @Override
    public void accept(StatementVisitor v) {
        for (var child : children) {
            child.accept(v);
        }
    }
}
