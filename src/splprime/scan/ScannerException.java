package splprime.scan;

public class ScannerException extends RuntimeException {

    public ScannerException(int line, String message) {
        super(String.format("L%d: %s", line, message));
    }

}
