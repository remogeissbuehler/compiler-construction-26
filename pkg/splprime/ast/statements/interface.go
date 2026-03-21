package statements

import "github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/expressions"

type Visitor interface {
	VisitIf(*If)
	VisitVar(*Var)
	VisitPrint(*Print)
	VisitWhile(*While)
	VisitExpressionStatement(*ExpressionStatement)
}

type Statement interface {
	Accept(Visitor)
}

type Expr = expressions.Expression
