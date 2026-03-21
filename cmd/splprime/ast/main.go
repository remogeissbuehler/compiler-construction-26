package main

import (
	"fmt"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/expressions"
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/statements"
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/visitor"
)

func ifStatement() statements.Statement {
	return statements.NewIfElseStatement(
		expressions.NewBinaryUnchecked(
			expressions.NewLiteralUnchecked(
				scan.NewToken(scan.Ident, "i", nil, 17),
			),
			scan.NewToken(scan.OpEqual, "==", nil, 17),
			expressions.NewLiteralUnchecked(
				scan.NewToken(scan.Ident, "s", nil, 17),
			),
		),
		statements.NewBlockWith(
			statements.NewPrint(
				expressions.NewLiteralUnchecked(
					scan.NewToken(scan.LitString, "\"yes\"", "yes", 18),
				),
			),
		),
		statements.NewBlockWith(
			statements.NewPrint(
				expressions.NewLiteralUnchecked(
					scan.NewToken(scan.LitString, "\"no\"", "no", 20),
				),
			),
		),
	)
}

func main() {
	stmt := ifStatement()
	visitor := visitor.NewASTPrinter()
	stmt.Accept(visitor)

	fmt.Println(visitor.Finish())
}
