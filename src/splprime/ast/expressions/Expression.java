package splprime.ast.expressions;

import splprime.visitor.ExpressionVisitor;

public abstract class Expression {
    abstract void accept(ExpressionVisitor v);

}
