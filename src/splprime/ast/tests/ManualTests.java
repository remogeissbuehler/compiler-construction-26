package splprime.ast.tests;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.ast.expressions.ArithmeticBinaryExpression;
import splprime.ast.expressions.ComparisonBinaryExpression;
import splprime.ast.expressions.LiteralExpression;
import splprime.ast.statements.Block;
import splprime.ast.statements.IfStatement;
import splprime.ast.statements.PrintStatement;
import splprime.ast.statements.Statement;
import splprime.ast.statements.VariableDeclaration;
import splprime.scan.Token;
import splprime.scan.TokenType;
import splprime.visitor.ASTPrinter;

public class ManualTests {

    private static Statement ifStatement() throws UnexpectedTokenException {
        return new IfStatement(
                new ComparisonBinaryExpression(
                        new LiteralExpression(
                                new Token(TokenType.IDENT, "i", null, 17)),
                        new Token(TokenType.OP_EQUAL, "==", null, 17),
                        new LiteralExpression(
                                new Token(TokenType.IDENT, "s", null, 17))),
                new Block(
                        new PrintStatement(
                                new LiteralExpression(
                                        new Token(TokenType.LIT_STRING, "\"yes\"", "yes", 18)))

                ),
                new Block(
                        new PrintStatement(
                                new LiteralExpression(
                                        new Token(TokenType.LIT_STRING, "\"no\"", "no", 20)))));

    }

    private static Statement varStatement() throws UnexpectedTokenException {
        return new VariableDeclaration(
                "average",
                new ArithmeticBinaryExpression(
                        new ArithmeticBinaryExpression(
                                new LiteralExpression(new Token(TokenType.IDENT, "min", null, 10)),
                                new Token(TokenType.OP_PLUS, "+", null, 10),
                                new LiteralExpression(new Token(TokenType.IDENT, "max", null, 10))),
                        new Token(TokenType.OP_SLASH, "/", null, 10),
                        new LiteralExpression(new Token(TokenType.LIT_NUMBER, "2", 2, 10))));
    }

    public static void main(String[] args) throws Exception {
        var stmt = ifStatement();
        var visitor = new ASTPrinter();
        stmt.accept(visitor);

        System.out.println("--- IF STATEMENT ---");
        System.out.print(visitor.finish());

        stmt = varStatement();
        visitor = new ASTPrinter();
        stmt.accept(visitor);

        System.out.println("--- VAR STATEMENT ---");
        System.out.print(visitor.finish());
    }

}
