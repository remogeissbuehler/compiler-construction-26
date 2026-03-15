package scan

import (
	"errors"
	"strconv"
	"unicode"

	"github.com/remogeissbuehler/compiler-construction/pkg/option"
)

func isAlphaNumeric(r option.Option[rune]) bool {
	return r.IsSomeAnd(func(x rune) bool {
		return unicode.IsLetter(x) || unicode.IsDigit(x)
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
	s.addTokenWithLiteral(typ, nil)
}

func (s *Scanner) addTokenWithLiteral(typ TokenType, literal any) {
	tok := NewToken(typ, s.currentLexeme(), literal, s.line)
	s.tokens = append(s.tokens, tok)
}

func (s *Scanner) handleNumber() error {
	for s.peek().IsSomeAnd(unicode.IsDigit) {
		s.advance()
	}

	if s.match('.') {
		for s.peek().IsSomeAnd(unicode.IsDigit) {
			s.advance()
		}
	}

	val, err := strconv.ParseFloat(s.currentLexeme(), 64)
	if err != nil {
		return errors.New("number error: cannot parse float from number")
	}

	s.addTokenWithLiteral(LitNumber, val)
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

	s.addTokenWithLiteral(LitString, text)
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
