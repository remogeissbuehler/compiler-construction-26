package statements

import (
	"github.com/remogeissbuehler/compiler-construction/pkg/option"
)

type If struct {
	Condition     Expr
	ThenStatement Statement
	ElseStatement option.Option[Statement]
}

func NewIfStatement(condition Expr, then Statement) *If {
	return &If{
		Condition:     condition,
		ThenStatement: then,
		ElseStatement: option.None[Statement](),
	}
}

func NewIfElseStatement(condition Expr, then Statement, els Statement) *If {
	return &If{
		Condition:     condition,
		ThenStatement: then,
		ElseStatement: option.Some(els),
	}
}

func (s *If) Accept(v Visitor) {
	v.VisitIf(s)
}
