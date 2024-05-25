package exceptions;

public class InvalidSongException extends RuntimeException {
    public InvalidSongException(String message) {
        super(message);
    }
}
