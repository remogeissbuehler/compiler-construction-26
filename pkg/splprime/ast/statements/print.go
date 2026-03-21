package statements

type Print struct {
	Expression Expr
}

func NewPrint(expr Expr) *Print {
	return &Print{
		Expression: expr,
	}
}

func (s *Print) Accept(v Visitor) {
	v.VisitPrint(s)
}
