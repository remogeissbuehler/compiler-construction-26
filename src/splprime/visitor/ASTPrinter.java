package splprime.visitor;

import java.util.NoSuchElementException;

import splprime.ast.expressions.*;
import splprime.ast.statements.*;

public class ASTPrinter implements ASTVisitor {
    StringBuilder sb = new StringBuilder();
    int nbIndents = 0;

    public String finish() {
        return sb.toString();
    }

    private void indent() {
        sb.append("  ".repeat(nbIndents));
    }

    private void indentAppend(String s) {
        indent();
        sb.append(s);
        sb.append("\n");
    }

    private void visitBinary(AbstractBinaryExpression e, String name) {
        indentAppend(name);
        nbIndents++;
        e.left.accept(this);
        indentAppend(e.operator.toString());
        e.right.accept(this);
        nbIndents--;

    }

    @Override
    public void visit(AssignmentExpression e) {
        indentAppend("Assignment");
        nbIndents++;
        indentAppend("identifier: " + e.identifier);
        e.value.accept(this);
        nbIndents--;

    }

    @Override
    public void visit(ComparisonBinaryExpression e) {
        visitBinary(e, "ComparisonBinaryExpression");

    }

    @Override
    public void visit(LiteralExpression e) {
        indentAppend("LiteralExpr: " + e.value.toString());
    }

    @Override
    public void visit(ArithmeticBinaryExpression arithmeticExpression) {
        visitBinary(arithmeticExpression, "ArithmeticBinaryExpression");

    }

    @Override
    public void visit(LogicalBinaryExpression logicalBinaryExpression) {
        visitBinary(logicalBinaryExpression, "LogicalBinaryExpression");
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        indentAppend("UnaryExpression");
        nbIndents++;
        indentAppend("op: " + unaryExpression.operator.toString());

        unaryExpression.expr.accept(this);
        nbIndents--;
    }

    @Override
    public void visit(PrintStatement st) {
        indentAppend("PrintStatement");
        nbIndents++;
        st.expr.accept(this);
        nbIndents--;
    }

    @Override
    public void visit(IfStatement st) {
        indentAppend("IfStatement");
        nbIndents++;
        st.condition.accept(this);
        st.thenStatement.accept(this);

        try {
            st.elseStatement.orElseThrow().accept(this);
        } catch (NoSuchElementException _) {
        }

        nbIndents--;
    }

    @Override
    public void visit(WhileStatement st) {
        indentAppend("WhileStatement");

        nbIndents++;
        st.condition.accept(this);
        st.doStatement.accept(this);
        nbIndents--;

    }

    @Override
    public void visit(ExpressionStatement st) {
        st.expression.accept(this);
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        indentAppend("VariableDeclaration");
        nbIndents++;
        indentAppend("Name: " + variableDeclaration.name);

        try {
            variableDeclaration.initializer.orElseThrow().accept(this);
        } catch (NoSuchElementException _) {
        }

        nbIndents--
    }

}
