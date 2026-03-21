package splprime.scan;

public enum TokenType {
	/* OPERATORS */
	OP_PLUS,
	OP_MINUS,
	OP_STAR,
	OP_SLASH,
	OP_ASSIGN,
	OP_EQUAL,
	OP_NOT_EQUAL,
	OP_GREATER_THAN,
	OP_LESS_THAN,
	OP_GEQ, // Greater than or equal
	OP_LEQ, // Less than or equal
	OP_BANG,

	/* SPECIAL CHARS */
	CHAR_SEMICOLON,
	CHAR_L_PAREN,
	CHAR_R_PAREN,
	CHAR_L_BRACE,
	CHAR_R_BRACE,

	/* KEYWWORDS */
	KEYWORD_TRUE,
	KEYWORD_FALSE,
	KEYWORD_AND,
	KEYWORD_OR,
	KEYWORD_VAR,
	KEYWORD_PRINT,
	KEYWORD_IF,
	KEYWORD_ELSE,
	KEYWORD_WHILE,

	/* LITERALS */
	LIT_STRING,
	LIT_NUMBER,

	/* IDENTIFIERS */
	IDENT,

	/* EOF */
	EOF,
}

class TokenResolver {
	static TokenType resolveKeywordOrNull(String s) {
		switch (s) {
			case "true":
				return TokenType.KEYWORD_TRUE;
			case "false":
				return TokenType.KEYWORD_FALSE;
			case "and":
				return TokenType.KEYWORD_AND;
			case "or":
				return TokenType.KEYWORD_OR;
			case "var":
				return TokenType.KEYWORD_VAR;
			case "print":
				return TokenType.KEYWORD_PRINT;
			case "if":
				return TokenType.KEYWORD_IF;
			case "else":
				return TokenType.KEYWORD_ELSE;
			case "while":
				return TokenType.KEYWORD_WHILE;
			default:
				return null;
		}
	}

	static TokenType resolveKeywordOrIdent(String s) {
		var t = resolveKeywordOrNull(s);
		if (t == null) {
			return TokenType.IDENT;
		}

		return t;
	}

	static TokenType resolveStatic(String s) {

		switch (s) {
			/* OPERATORS */
			case "+":
				return TokenType.OP_PLUS;
			case "-":
				return TokenType.OP_MINUS;
			case "*":
				return TokenType.OP_STAR;
			case "/":
				return TokenType.OP_SLASH;
			case "=":
				return TokenType.OP_ASSIGN;
			case "==":
				return TokenType.OP_EQUAL;
			case "!=":
				return TokenType.OP_NOT_EQUAL;
			case ">":
				return TokenType.OP_GREATER_THAN;
			case ">=":
				return TokenType.OP_GEQ;
			case "<":
				return TokenType.OP_LESS_THAN;
			case "<=":
				return TokenType.OP_LEQ;
			case "!":
				return TokenType.OP_BANG;

			/* SPECIAL CHARS */
			case ";":
				return TokenType.CHAR_SEMICOLON;
			case "(":
				return TokenType.CHAR_L_PAREN;
			case ")":
				return TokenType.CHAR_R_PAREN;
			case "{":
				return TokenType.CHAR_L_BRACE;
			case "}":
				return TokenType.CHAR_R_BRACE;

			default:
				var t = resolveKeywordOrNull(s);
				if (t == null)
					throw new RuntimeException("invalid static token: " + s);
				else
					return t;
		}
	}
}
