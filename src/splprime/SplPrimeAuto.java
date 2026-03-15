package splprime;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import splprime.scan.Scanner;
import splprime.autoscan.Token;

import splprime.autoscan.SplPrimeAutoParser;
import splprime.autoscan.SplPrimeAutoParserConstants;

public class SplPrimeAuto {

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
		var stream = new FileInputStream(path);
		var scanner = new SplPrimeAutoParser(stream);

		Token t = scanner.getNextToken();
		while (t.kind != SplPrimeAutoParserConstants.EOF) {
			var typ = SplPrimeAutoParserConstants.tokenImage[t.kind];
			System.out.printf("<%s, %s>\n", typ, t);
			t = scanner.getNextToken();
		}
		// Indicate an error in the exit code.

		if (hadError) {
			System.exit(65);
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
