package splprime.parse;

import java.util.ArrayList;
import java.util.List;

import splprime.ast.expressions.ArithmeticBinaryExpression;
import splprime.ast.expressions.AssignmentExpression;
import splprime.ast.expressions.ComparisonBinaryExpression;
import splprime.ast.expressions.Expression;
import splprime.ast.expressions.LiteralExpression;
import splprime.ast.expressions.LogicalBinaryExpression;
import splprime.ast.expressions.UnaryExpression;
import splprime.ast.expressions.VariableExpression;
import splprime.ast.statements.Block;
import splprime.ast.statements.ExpressionStatement;
import splprime.ast.statements.IfStatement;
import splprime.ast.statements.PrintStatement;
import splprime.ast.statements.Statement;
import splprime.ast.statements.VariableDeclaration;
import splprime.ast.statements.WhileStatement;
import splprime.scan.Token;
import splprime.scan.TokenType;

public class Parser {
    private List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token consume(TokenType expected) throws UnexptectedTokenException {
        Token actual = current();
        if (actual.type == expected) {
            next();
            return actual;
        }

        throw new UnexptectedTokenException(actual, expected);

    }

    private Token consumeAny(TokenType... expected) throws UnexptectedTokenException {
        Token actual = current();

        for (var ex : expected) {
            if (actual.type == ex) {
                next();
                return actual;
            }
        }

        throw new UnexptectedTokenException(actual, expected);

    }

    private Token next() {
        return this.tokens.get(current++);
    }

    private Token lookahead() {
        return this.tokens.get(current + 1);
    }

    private Expression expression() throws SyntaxError {
        return assignment();
    }

    private Expression assignment() throws SyntaxError {
        if (current().type == TokenType.IDENT && lookahead().type == TokenType.OP_ASSIGN) {
            Token iden = consume(TokenType.IDENT);
            consume(TokenType.OP_ASSIGN);
            Expression assignment = assignment();
            return new AssignmentExpression(iden, assignment);
        }

        return logic_or();
    }

    private Expression logic_or() throws SyntaxError {
        Expression result = logic_and();

        while (current().type == TokenType.KEYWORD_OR) {
            Token op = consume(TokenType.KEYWORD_OR);
            Expression right = logic_and();
            result = new LogicalBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression logic_and() throws SyntaxError {
        Expression result = equality();
        while (current().type == TokenType.KEYWORD_AND) {
            Token op = consume(TokenType.KEYWORD_AND);
            Expression right = equality();
            result = new LogicalBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression equality() throws SyntaxError {
        Expression result = comparison();

        while (current().type == TokenType.OP_EQUAL || current().type == TokenType.OP_NOT_EQUAL) {
            Token op = consumeAny(TokenType.OP_EQUAL, TokenType.OP_NOT_EQUAL);
            Expression right = comparison();
            result = new ComparisonBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression comparison() throws SyntaxError {
        Expression result = term();

        while (current().type == TokenType.OP_LESS_THAN
                || current().type == TokenType.OP_LEQ
                || current().type == TokenType.OP_GREATER_THAN
                || current().type == TokenType.OP_GEQ) {

            Token op = consumeAny(TokenType.OP_LESS_THAN, TokenType.OP_LEQ, TokenType.OP_GREATER_THAN,
                    TokenType.OP_GEQ);
            Expression right = term();
            result = new ComparisonBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression term() throws SyntaxError {
        Expression result = factor();

        while (current().type == TokenType.OP_PLUS || current().type == TokenType.OP_MINUS) {
            Token op = consumeAny(TokenType.OP_PLUS, TokenType.OP_MINUS);
            Expression right = factor();
            result = new ArithmeticBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression factor() throws SyntaxError {
        Expression result = unary();

        while (current().type == TokenType.OP_SLASH || current().type == TokenType.OP_STAR) {
            Token op = consumeAny(TokenType.OP_SLASH, TokenType.OP_STAR);
            Expression right = unary();
            result = new ArithmeticBinaryExpression(result, op, right);
        }

        return result;
    }

    private Expression unary() throws SyntaxError {
        switch (current().type) {
            case TokenType.OP_BANG, TokenType.OP_MINUS:
                Token op = consumeAny(TokenType.OP_BANG, TokenType.OP_MINUS);
                Expression rest = unary();
                return new UnaryExpression(op, rest);
            default:
                return primary();

        }
    }

    private Expression primary() throws SyntaxError {
        switch (current().type) {
            case KEYWORD_TRUE, KEYWORD_FALSE:
                Token boolVal = consumeAny(TokenType.KEYWORD_TRUE, TokenType.KEYWORD_FALSE);
                return new LiteralExpression(boolVal);

            case LIT_NUMBER, LIT_STRING:
                Token lit = consumeAny(TokenType.LIT_NUMBER, TokenType.LIT_STRING);
                return new LiteralExpression(lit);

            case CHAR_L_PAREN:
                consume(TokenType.CHAR_L_PAREN);
                Expression expr = expression();
                consume(TokenType.CHAR_R_PAREN);

                return expr;

            case IDENT:
                Token var = consume(TokenType.IDENT);
                return new VariableExpression(var);

            default:
                throw new UnexptectedTokenException(current(), TokenType.KEYWORD_TRUE, TokenType.KEYWORD_FALSE,
                        TokenType.LIT_NUMBER, TokenType.LIT_STRING, TokenType.CHAR_L_PAREN, TokenType.IDENT);

        }
    }

    private Statement varDecl() throws SyntaxError {
        consume(TokenType.KEYWORD_VAR);
        Token iden = consume(TokenType.IDENT);

        if (current().type == TokenType.OP_ASSIGN) {
            consume(TokenType.OP_ASSIGN);
            Expression init = expression();
            consume(TokenType.CHAR_SEMICOLON);
            return new VariableDeclaration(iden, init);
        }

        consume(TokenType.CHAR_SEMICOLON);
        return new VariableDeclaration(iden);
    }

    private Statement ifStmt() throws SyntaxError {
        consume(TokenType.KEYWORD_IF);
        consume(TokenType.CHAR_L_PAREN);
        Expression condition = expression();
        consume(TokenType.CHAR_R_PAREN);
        Statement stmt = statement();

        if (current().type == TokenType.KEYWORD_ELSE) {
            consume(TokenType.KEYWORD_ELSE);
            Statement els = statement();
            return new IfStatement(condition, stmt, els);

        }

        return new IfStatement(condition, stmt);
    }

    private Statement printStmt() throws SyntaxError {
        consume(TokenType.KEYWORD_PRINT);
        Expression printExpression = expression();
        consume(TokenType.CHAR_SEMICOLON);

        return new PrintStatement(printExpression);
    }

    private Statement whileStmt() throws SyntaxError {
        consume(TokenType.KEYWORD_WHILE);
        consume(TokenType.CHAR_L_PAREN);
        Expression cond = expression();
        consume(TokenType.CHAR_R_PAREN);
        Statement stmt = statement();

        return new WhileStatement(cond, stmt);
    }

    private Statement block() throws SyntaxError {
        consume(TokenType.CHAR_L_BRACE);

        Block block = new Block();
        while (current().type != TokenType.CHAR_R_BRACE) {
            Statement stmt = statement();
            block.add(stmt);
        }

        consume(TokenType.CHAR_R_BRACE);
        return block;
    }

    private Statement exprStmt() throws SyntaxError {
        Expression expr = expression();
        consume(TokenType.CHAR_SEMICOLON);

        return new ExpressionStatement(expr);

    }

    private Statement statement() throws SyntaxError {
        switch (current().type) {
            case TokenType.KEYWORD_IF:
                return ifStmt();
            case TokenType.KEYWORD_PRINT:
                return printStmt();
            case TokenType.KEYWORD_WHILE:
                return whileStmt();
            case TokenType.CHAR_L_BRACE:
                return block();
            default:
                return exprStmt();
        }
    }

    private Statement declaration() throws SyntaxError {
        switch (current().type) {
            case TokenType.KEYWORD_VAR:
                return varDecl();
            default:
                return statement();
        }

    }

    private Token current() {
        return this.tokens.get(current);
    }

    public List<Statement> parse() throws SyntaxError {
        List<Statement> statements = new ArrayList<>();

        while (!current().isEOF()) {
            statements.add(declaration());
        }

        return statements;
    }
}
