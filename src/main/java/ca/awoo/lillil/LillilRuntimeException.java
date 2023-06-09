package ca.awoo.lillil;

/**
 * An exception thrown by lillil.
 */
public class LillilRuntimeException extends Exception {
    public LillilRuntimeException(String message) {
        super(message);
    }

    public LillilRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
