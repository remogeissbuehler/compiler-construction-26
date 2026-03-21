package statements

import "github.com/remogeissbuehler/compiler-construction/pkg/option"

type Var struct {
	Name        string
	Initializer option.Option[Expr]
}

func NewVariableDeclaration(name string) *Var {
	return &Var{
		Name:        name,
		Initializer: option.None[Expr](),
	}
}

func NewVariableInitialization(name string, init Expr) *Var {
	return &Var{
		Name:        name,
		Initializer: option.Some(init),
	}
}

func (s *Var) Accept(v Visitor) {
	v.VisitVar(s)
}
