package expressions

import (
	"errors"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"
)

type Unary struct {
	Operator Token
	Expr     Expression
}

func checkUnaryOperatorToken(tt scan.TokenType) bool {
	return tt == scan.OpMinus || tt == scan.OpBang
}

func NewUnaryUnchecked(operator Token, expr Expression) *Unary {
	return &Unary{
		Operator: operator,
		Expr:     expr,
	}
}

func NewUnary(operator Token, expr Expression) (*Unary, error) {
	valid := checkUnaryOperatorToken(operator.Type())
	if !valid {
		return nil, errors.New("unexpected token: " + operator.String())
	}

	return &Unary{
		Operator: operator,
		Expr:     expr,
	}, nil
}

func (e *Unary) Accept(v Visitor) {
	v.VisitUnary(e)
}
