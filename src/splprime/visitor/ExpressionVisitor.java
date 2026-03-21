package splprime.visitor;

import splprime.ast.expressions.*;

public interface ExpressionVisitor {
    public void visit(AssignmentExpression e);

    public void visit(ComparisonBinaryExpression e);

    public void visit(LiteralExpression e);

    public void visit(ArithmeticBinaryExpression arithmeticExpression);

    public void visit(LogicalBinaryExpression logicalBinaryExpression);

    public void visit(UnaryExpression unaryExpression);
}
