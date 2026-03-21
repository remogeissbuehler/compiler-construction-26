package expressions

import "github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"

type Expression interface {
	Accept(Visitor)
}

type Visitor interface {
	VisitBinary(*Binary)
	VisitUnary(*Unary)
	VisitLiteral(*Literal)
	VisitAssignment(*Assignment)
}

type Token = scan.Token
