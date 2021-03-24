package it.unipd.dei.yourwaytoitaly;

/**
 * Class to represents both informative and error messages
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class Message {
    private final String message;
    private final String errorCode;
    private final String errorDetails;
    private final boolean isError;
    public Message(final String message, final String errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }
    public Message(final String message) {
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }
    public final String getMessage() {
        return message;
    }
    public final String getErrorCode() {
        return errorCode;
    }
    public final String getErrorDetails() {
        return errorDetails;
    }
    public final boolean isError() {
        return isError;
    }
}
