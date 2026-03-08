package splprime.scan;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

public class Scanner {

	// In and output
	private final String source;
	private final List<Token> tokens = new ArrayList<>();
	private int current = 0;
	private int start = 0;
	private int line = 1;

	private boolean atEnd() {
		return current == source.length();
	}

	private char advance() {
		return source.charAt(current++);
	}

	private boolean match(char expected) {
		if (atEnd())
			return false;

		if (source.charAt(current) != expected)
			return false;

		current++;
		return true;
	}

	private Optional<Character> peek() {
		if (atEnd()) {
			return Optional.empty();
		}

		return Optional.of(source.charAt(current));

	}

	public Scanner(String source) {
		this.source = source;
	}

	private String currentLexeme() {
		return source.substring(start, current);
	}

	private void addToken(TokenType type) {
		Token tok = new Token(type, currentLexeme(), type, line);
		tokens.add(tok);
	}

	private void addToken(TokenType type, String lexeme) {
		Token tok = new Token(type, lexeme, type, line);
		tokens.add(tok);
	}

	/*
	 * private TokenType resolveSingleCharacterTokenType(char c) {
	 * switch (c) {
	 * case '+':
	 * return TokenType.OP_PLUS;
	 * case '-':
	 * return TokenType.OP_MINUS;
	 * case '*':
	 * return TokenType.OP_STAR;
	 * case ';':
	 * return TokenType.CHAR_SEMICOLON;
	 * case '(':
	 * return TokenType.CHAR_L_PAREN;
	 * case ')':
	 * return TokenType.CHAR_R_PAREN;
	 * case '{':
	 * return TokenType.CHAR_L_BRACE;
	 * case '}':
	 * return TokenType.CHAR_R_BRACE;
	 * 
	 * default:
	 * throw new RuntimeException("invalid single character token: " + c);
	 * }
	 * }
	 */
	private void handleNumber(char c) {
		boolean hasDecimal = false;
		boolean hasAfterDecimal = false;
		boolean done = false;

		while (!done) {
			char next = peek().orElse('\n');
			switch (next) {
				case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
					advance();
					hasAfterDecimal = hasDecimal;

					break;
				case '.':
					if (hasDecimal) {
						throw new ScannerException(line, "number error: expected at most one decimal point.");
					}

					hasDecimal = true;
					advance();

					break;
				default:
					done = true;
					if (hasDecimal && !hasAfterDecimal) {
						throw new ScannerException(line, "number error: expected numbers after decimal point.");
					}

					break;
			}
		}

		addToken(TokenType.LIT_NUMBER);
	}

	private static boolean isAlphaNumeric(Optional<Character> c) {
		if (c.isEmpty()) {
			return false;
		}

		char chr = c.get();
		return Character.isAlphabetic(chr) || chr >= '0' && chr <= '9';
	}

	private void handleAlpha(char c) {
		while (isAlphaNumeric(peek()))
			advance();

		String text = currentLexeme();

		var type = TokenResolver.resolveKeywordOrIdent(text);
		addToken(type);
	}

	private void handleString(char c) {
		while (peek().orElse('\n') != '"')
			advance();

		if (!match('"'))
			throw new ScannerException(line, "lexer error: unterminated string");

		// remove quotes from lexeme
		String text = currentLexeme();
		text = text.substring(1, text.length() - 1);

		addToken(TokenType.LIT_STRING, text);
	}

	// Scan tokens
	public List<Token> scanTokens() {

		while (!atEnd()) {
			start = current;
			char c = advance();
			TokenType type;

			switch (c) {
				case '\n':
					line++;
					// throw new RuntimeException();
					break;
				case '+', '-', '*', ';', '(', ')', '{', '}', '!':
					type = TokenResolver.resolveStatic(String.valueOf(c));
					addToken(type);
					break;
				case '/':
					if (match('/')) {
						while (peek().orElse('\n') != '\n')
							advance();

					} else {
						addToken(TokenType.OP_SLASH);
					}
					break;
				case '=', '<', '>':
					var tokStr = String.valueOf(c);

					if (match('=')) {
						tokStr += '=';
					}
					addToken(TokenResolver.resolveStatic(tokStr));
					break;
				case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
					handleNumber(c);
					break;
				case '"':
					handleString(c);
					break;
				default:
					if (Character.isAlphabetic(c)) {
						handleAlpha(c);
						continue;
					}
					if (Character.isWhitespace(c)) {
						continue;
					}

					throw new ScannerException(line, "lexical error: unexpected token: " + c);
			}

		}
		start = current;
		addToken(TokenType.EOF);

		return tokens;
	}

}
