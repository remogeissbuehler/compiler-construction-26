package splprime.visitor;

import splprime.ast.statements.*;

public interface StatementVisitor {
    public void visit(PrintStatement st);

    public void visit(IfStatement st);

    public void visit(WhileStatement st);

    public void visit(ExpressionStatement st);

    public void visit(VariableDeclaration variableDeclaration);
}
