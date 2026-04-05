package splprime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import splprime.parse.Parser;
import splprime.parse.SyntaxError;
import splprime.scan.Scanner;
import splprime.scan.Token;
import splprime.visitor.ASTPrinter;

public class SplPrime {

	static boolean hadError = false;

	// Expects a single file that comprises SPL' program as argument
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("expected exactly one file to parse");
			System.exit(1);
		}
		runFile(args[0]);
	}

	private static void runFile(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		run(new String(bytes, Charset.defaultCharset()));

		// Indicate an error in the exit code.
		if (hadError) {
			System.exit(65);
		}
	}

	private static void run(String source) {
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scanTokens();

		// For now, just print the tokens
		System.out.println("========= TOKENs =========");
		for (Token token : tokens) {
			System.out.println(token);
		}

		Parser parser = new Parser(tokens);
		try {
			var stmts = parser.parse();

			System.out.println("========= STATEMENTs =========");
			for (var stmt : stmts) {
				System.out.println("-------------");
				var printer = new ASTPrinter();
				stmt.accept(printer);
				System.out.println(printer.finish());
			}
		} catch (SyntaxError e) {
			System.err.println("AAAH");
			System.err.println(e);
		}

	}

	public static void error(int line, String message) {
		report(line, "", message);
	}

	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error" + where + ": " + message);
		hadError = true;
	}

}
