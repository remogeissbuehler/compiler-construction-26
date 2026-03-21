package visitor

import (
	"strings"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/expressions"
	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/ast/statements"
)

type ASTPrinter struct {
	builder   strings.Builder
	nbIndents int
}

func NewASTPrinter() *ASTPrinter {
	return &ASTPrinter{
		builder:   strings.Builder{},
		nbIndents: 0,
	}
}

func (a *ASTPrinter) indentAppend(s string) {
	indent := strings.Repeat("  ", a.nbIndents)
	a.builder.WriteString(indent)
	a.builder.WriteString(s)
	a.builder.WriteString("\n")
}

func (a *ASTPrinter) Finish() string {
	return a.builder.String()
}

// VisitAssignment implements [ASTVisitor].
func (a *ASTPrinter) VisitAssignment(expr *expressions.Assignment) {
	a.indentAppend("Assignment")
	a.nbIndents++

	a.indentAppend("identifier: " + expr.Identifier)
	expr.Value.Accept(a)

	a.nbIndents--
}

// VisitBinary implements [ASTVisitor].
func (a *ASTPrinter) VisitBinary(expr *expressions.Binary) {
	a.indentAppend("BinaryExpression")
	a.nbIndents++

	expr.Left.Accept(a)
	a.indentAppend(expr.Operator.String())
	expr.Right.Accept(a)

	a.nbIndents--
}

// VisitExpressionStatement implements [ASTVisitor].
func (a *ASTPrinter) VisitExpressionStatement(st *statements.ExpressionStatement) {
	st.Expression.Accept(a)
}

// VisitIf implements [ASTVisitor].
func (a *ASTPrinter) VisitIf(st *statements.If) {
	a.indentAppend("IfStatement")
	a.nbIndents++

	st.Condition.Accept(a)
	st.ThenStatement.Accept(a)
	st.ElseStatement.IfSomeThen(func(est statements.Statement) {
		est.Accept(a)
	})

	a.nbIndents--
}

// VisitLiteral implements [ASTVisitor].
func (a *ASTPrinter) VisitLiteral(expr *expressions.Literal) {
	a.indentAppend("LiteralExpr: " + expr.Value.String())
}

// VisitPrint implements [ASTVisitor].
func (a *ASTPrinter) VisitPrint(st *statements.Print) {
	a.indentAppend("PrintStatement")
	a.nbIndents++

	st.Expression.Accept(a)

	a.nbIndents--
}

// VisitUnary implements [ASTVisitor].
func (a *ASTPrinter) VisitUnary(expr *expressions.Unary) {
	a.indentAppend("UnaryExpression")
	a.nbIndents++

	a.indentAppend("op: " + expr.Operator.String())
	expr.Expr.Accept(a)

	a.nbIndents--
}

// VisitVar implements [ASTVisitor].
func (a *ASTPrinter) VisitVar(st *statements.Var) {
	a.indentAppend("VariableDeclaration")
	a.nbIndents++

	a.indentAppend("Name: " + st.Name)
	st.Initializer.IfSomeThen(func(init statements.Expr) {
		init.Accept(a)
	})

	a.nbIndents--
}

// VisitWhile implements [ASTVisitor].
func (a *ASTPrinter) VisitWhile(st *statements.While) {
	a.indentAppend("WhileStatement")
	a.nbIndents++

	st.Condition.Accept(a)
	st.DoStatement.Accept(a)

	a.nbIndents--
}
