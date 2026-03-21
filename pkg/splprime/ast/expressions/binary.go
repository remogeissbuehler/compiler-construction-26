package expressions

import (
	"errors"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"
)

type Binary struct {
	Left     Expression
	Operator Token
	Right    Expression
}

func NewBinaryUnchecked(left Expression, operator Token, right Expression) *Binary {
	return &Binary{
		Left:     left,
		Operator: operator,
		Right:    right,
	}
}

func checkBinaryOperatorToken(tt scan.TokenType) bool {
	valid := false

	valid = valid || tt == scan.OpPlus
	valid = valid || tt == scan.OpMinus
	valid = valid || tt == scan.OpStar
	valid = valid || tt == scan.OpSlash
	valid = valid || tt == scan.OpEqual
	valid = valid || tt == scan.OpNotEqual
	valid = valid || tt == scan.OpGreaterThan
	valid = valid || tt == scan.OpLessThan
	valid = valid || tt == scan.OpGeq
	valid = valid || tt == scan.OpLeq
	valid = valid || tt == scan.KeywordAnd
	valid = valid || tt == scan.KeywordOr

	return valid
}

func NewBinary(left Expression, operator Token, right Expression) (*Binary, error) {
	valid := checkBinaryOperatorToken(operator.Type())
	if !valid {
		return nil, errors.New("unexpected token: " + operator.String())
	}

	return &Binary{
		Left:     left,
		Operator: operator,
		Right:    right,
	}, nil
}

func (e *Binary) Accept(v Visitor) {
	v.VisitBinary(e)
}
