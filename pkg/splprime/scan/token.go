package scan

import "fmt"

type Token struct {
	typ     TokenType
	lexeme  string
	literal any
	line    int
}

func NewToken(typ TokenType, lexeme string, literal any, line int) Token {
	return Token{typ, lexeme, literal, line}
}

func (t Token) Type() TokenType {
	return t.typ
}

func (t Token) String() string {
	// return "<" + type + "," + lexeme + "> " + "Literal: " + literal + ", Line: " + line;
	return fmt.Sprintf("< %s, %s > Literal: %v, Line: %d", t.typ, t.lexeme, t.literal, t.line)
}
