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

func (t Token) String() string {
	return fmt.Sprintf("<%s,%s,%s,%d>", t.typ, t.lexeme, t.literal, t.line)
}
