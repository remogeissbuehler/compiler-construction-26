package visitor

import (
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/expressions"
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/statements"
)

type ASTVisitor interface {
	statements.Visitor
	expressions.Visitor
}
