package exceptions;

public class InvalidPlaylistException extends RuntimeException {
    public InvalidPlaylistException(String message) {
        super(message);
    }
}
