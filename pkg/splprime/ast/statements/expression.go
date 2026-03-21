package statements

type ExpressionStatement struct {
	Expression Expr
}

func NewExpressionStatement(expr Expr) *ExpressionStatement {
	return &ExpressionStatement{
		Expression: expr,
	}
}

func (s *ExpressionStatement) Accept(v Visitor) {
	v.VisitExpressionStatement(s)
}
