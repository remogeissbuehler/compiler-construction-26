package main

import (
	"fmt"
	"os"

	"github.com/remogeissbuehler/compiler-construction/pkg/splprime/scan"
)

func main() {
	args := os.Args

	if len(args) != 2 {
		fmt.Fprintln(os.Stderr, "expected exactly one file to parse")
		os.Exit(1)
	}

	runFile(args[1])
}

func runFile(path string) {
	source, err := os.ReadFile(path)
	if err != nil {
		panic(err)
	}

	scanner := scan.NewScanner(string(source))
	tokens, err := scanner.ScanTokens()
	if err != nil {
		panic(err)
	}

	for _, tok := range tokens {
		fmt.Println(tok)
	}
}

/*
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
		for (Token token : tokens) {
			System.out.println(token);
		}
	}
*/
