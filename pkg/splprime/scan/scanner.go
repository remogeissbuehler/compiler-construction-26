package scan

import (
	"errors"
	"unicode"

	"github.com/remogeissbuehler/compiler-construction/pkg/option"
)

func isAlphaNumeric(r option.Option[rune]) bool {
	return r.IsSomeAnd(func(x rune) bool {
		return unicode.IsLetter(x) || unicode.IsNumber(x)
	})
}

type Scanner struct {
	source  []rune
	tokens  []Token
	current int
	start   int
	line    int
}

func NewScanner(source string) Scanner {
	return Scanner{
		source:  []rune(source),
		tokens:  make([]Token, 0, 64),
		current: 0,
		start:   0,
		line:    1,
	}
}

func (s Scanner) atEnd() bool {
	return s.current == len(s.source)
}

func (s *Scanner) advance() rune {
	curr := s.current
	s.current++
	return s.source[curr]
}

func (s *Scanner) match(expected rune) bool {
	if s.atEnd() {
		return false
	}

	if s.source[s.current] != expected {
		return false
	}

	s.current++
	return true
}

func (s *Scanner) peek() option.Option[rune] {
	if s.atEnd() {
		return option.None[rune]()
	}

	return option.Some(s.source[s.current])
}

func (s *Scanner) currentLexeme() string {
	return string(s.source[s.start:s.current])
}

func (s *Scanner) addToken(typ TokenType) {
	s.addTokenWithLexeme(typ, s.currentLexeme())
}

func (s *Scanner) addTokenWithLexeme(typ TokenType, lexeme string) {
	tok := NewToken(typ, lexeme, s.currentLexeme(), s.line)
	s.tokens = append(s.tokens, tok)
}

func (s *Scanner) handleNumber() error {
	hasDecimal := false
	hasAfterDecimal := false
	done := false

	for !done {
		next := s.peek().OrElse('\n')
		switch next {
		case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
			s.advance()
			hasAfterDecimal = hasDecimal

		case '.':
			if hasDecimal {
				return errors.New("number error: expected at most one decimal point.")
			}

			hasDecimal = true
			s.advance()

		default:
			done = true
			if hasDecimal && !hasAfterDecimal {
				return errors.New("number error: expected numbers after decimal point.")
			}

		}
	}

	s.addToken(LitNumber)
	return nil
}

func (s *Scanner) handleAlpha() {
	for isAlphaNumeric(s.peek()) {
		s.advance()
	}

	text := s.currentLexeme()
	typ := ResolveKeywordOrIdent(text)
	s.addToken(typ)
}

func (s *Scanner) handleString() error {
	var n rune

	for {
		n = s.peek().OrElse('\n')
		if n == '"' || n == '\n' {
			break
		}

		s.advance()
	}

	if !s.match('"') {
		return errors.New("lexer error: unterminated string")
	}

	text := s.currentLexeme()
	text = text[1 : len(text)-1]

	s.addTokenWithLexeme(LitString, text)
	return nil
}

func (s *Scanner) ScanTokens() ([]Token, error) {
	for !s.atEnd() {
		s.start = s.current
		r := s.advance()

		switch r {

		case '\n':
			s.line++
		case '+', '-', '*', ';', '(', ')', '{', '}', '!':
			typ, err := ResolveStaticToken(string(r))
			if err != nil {
				return nil, err
			}
			s.addToken(typ)
		case '/':
			if s.match('/') {
				for s.peek().OrElse('\n') != '\n' {
					s.advance()
				}
			} else {
				s.addToken(OpSlash)
			}
		case '=', '<', '>':
			tokStr := string(r)

			if s.match('=') {
				tokStr += "="
			}

			typ, err := ResolveStaticToken(tokStr)
			if err != nil {
				return nil, err
			}
			s.addToken(typ)
		case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
			s.handleNumber()
		case '"':
			s.handleString()
		default:
			if unicode.IsLetter(r) {
				s.handleAlpha()
				continue
			}

			if unicode.IsSpace(r) {
				continue
			}

			return nil, errors.New("lexical error: unexpected token: " + string(r))
		}
	}
	s.start = s.current
	s.addToken(EOF)

	return s.tokens, nil
}
