package expressions

import (
	"errors"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"
)

type Literal struct {
	Value Token
}

func checkLiteralToken(tt scan.TokenType) bool {
	return tt == scan.LitNumber || tt == scan.LitString || tt == scan.Ident
}

func NewLiteralUnchecked(value Token) *Literal {
	return &Literal{
		Value: value,
	}
}

func NewLiteral(value Token) (*Literal, error) {
	valid := checkLiteralToken(value.Type())
	if !valid {
		return nil, errors.New("unexpected token: " + value.String())
	}

	return &Literal{
		Value: value,
	}, nil
}

func (e *Literal) Accept(v Visitor) {
	v.VisitLiteral(e)
}
