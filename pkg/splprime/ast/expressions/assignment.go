package expressions

type Assignment struct {
	Identifier string
	Value      Expression
}

func NewAssignment(ident string, val Expression) *Assignment {
	return &Assignment{
		Identifier: ident,
		Value:      val,
	}
}

func (e *Assignment) Accept(v Visitor) {
	v.VisitAssignment(e)
}
