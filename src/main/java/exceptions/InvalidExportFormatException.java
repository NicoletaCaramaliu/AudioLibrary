package exceptions;

public class InvalidExportFormatException extends RuntimeException {
    public InvalidExportFormatException(String message) {
        super(message);
    }
}
