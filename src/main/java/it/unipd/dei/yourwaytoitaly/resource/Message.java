package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class to represents both informative and error messages
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class Message extends Resource {
    private final String message;
    private final int errorCode;
    private final String errorDetails;
    private final boolean isError;
    public Message(final String message, final int errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }
    public Message(final String message) {
        this.message = message;
        this.errorCode = 0;
        this.errorDetails = null;
        this.isError = false;
    }
    public final String getMessage() {
        return message;
    }
    public final int getErrorCode() {
        return errorCode;
    }
    public final String getErrorDetails() {
        return errorDetails;
    }
    public final boolean isError() {
        return isError;
    }

    @Override
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("message");
        jg.writeStartObject();
        jg.writeStringField("message", message);
        if(errorCode != 0) {
            jg.writeNumberField("errorCode", errorCode);
        }
        if(errorDetails != null) {
            jg.writeStringField("errorDetails", errorDetails);
        }
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }
}
