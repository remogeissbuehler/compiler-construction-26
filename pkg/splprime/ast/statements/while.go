package statements

type While struct {
	Condition   Expr
	DoStatement Statement
}

func NewWhile(condition Expr, doStatement Statement) *While {
	return &While{condition, doStatement}
}

func (s *While) Accept(v Visitor) {
	v.VisitWhile(s)
}
