//go:generate stringer -type=TokenType
package scan

import "errors"

type TokenType int

const (
	Unknown TokenType = iota

	/* OPERATORS */
	OpPlus
	OpMinus
	OpStar
	OpSlash
	OpAssign
	OpEqual
	OpNotEqual
	OpGreaterThan
	OpLessThan
	OpGeq // Greater than or equal
	OpLeq // Less than or equal
	OpBang

	/* SPECIAL CHARS */
	CharSemicolon
	CharLParen
	CharRParen
	CharLBrace
	CharRBrace

	/* KEYWWORDS */
	KeywordTrue
	KeywordFalse
	KeywordAnd
	KeywordOr
	KeywordVar
	KeywordPrint
	KeywordIf
	KeywordElse
	KeywordWhile

	/* LITERALS */
	LitString
	LitNumber

	/* IDENTIFIERS */
	Ident

	/* EOF */
	EOF
)

func ResolveKeywordOrUnkown(s string) TokenType {
	switch s {
	case "true":
		return KeywordTrue
	case "false":
		return KeywordFalse
	case "and":
		return KeywordAnd
	case "or":
		return KeywordOr
	case "var":
		return KeywordVar
	case "print":
		return KeywordPrint
	case "if":
		return KeywordIf
	case "else":
		return KeywordElse
	case "while":
		return KeywordWhile
	default:
		return Unknown
	}
}

func ResolveKeywordOrIdent(s string) TokenType {
	t := ResolveKeywordOrUnkown(s)

	if t == Unknown {
		return Ident
	}

	return t
}

func ResolveStaticToken(s string) (TokenType, error) {
	switch s {
	// OPERATORS
	case "+":
		return OpPlus, nil
	case "-":
		return OpMinus, nil
	case "*":
		return OpStar, nil
	case "/":
		return OpSlash, nil
	case "=":
		return OpAssign, nil
	case "==":
		return OpEqual, nil
	case "!=":
		return OpNotEqual, nil
	case ">":
		return OpGreaterThan, nil
	case ">=":
		return OpGeq, nil
	case "<":
		return OpLessThan, nil
	case "<=":
		return OpLeq, nil
	case "!":
		return OpBang, nil

	// SPECIAL CHARS
	case ";":
		return CharSemicolon, nil
	case "(":
		return CharLParen, nil
	case ")":
		return CharRParen, nil
	case "{":
		return CharLBrace, nil
	case "}":
		return CharRBrace, nil

	default:
		t := ResolveKeywordOrUnkown(s)
		if t == Unknown {
			return Unknown, errors.New("invalid static token: " + s)
		} else {
			return t, nil
		}
	}
}
