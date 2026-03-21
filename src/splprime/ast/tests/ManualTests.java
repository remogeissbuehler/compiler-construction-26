package splprime.ast.tests;

import splprime.ast.exceptions.UnexpectedTokenException;
import splprime.ast.expressions.ComparisonBinaryExpression;
import splprime.ast.expressions.LiteralExpression;
import splprime.ast.statements.IfStatement;
import splprime.ast.statements.PrintStatement;
import splprime.ast.statements.Statement;
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
                new PrintStatement(
                        new LiteralExpression(
                                new Token(TokenType.LIT_STRING, "\"yes\"", "yes", 18))),
                new PrintStatement(
                        new LiteralExpression(
                                new Token(TokenType.LIT_STRING, "\"no\"", "no", 20))));

    }

    public static void main(String[] args) throws Exception {
        var stmt = ifStatement();
        var visitor = new ASTPrinter();
        stmt.accept(visitor);

        System.out.print(visitor.finish());
    }

}
