package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class to define the objects which represents the TypeAdvertisement in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class TypeAdvertisement extends Resource {
    private final int idType;
    private final String type;

    public TypeAdvertisement(final int idType, final String type) {
        this.idType = idType;
        this.type = type;
    }
    public final int getIdType() {
        return idType;
    }
    public final String getType() {
        return type;
    }

    /**
     * Writes the JSON representation to the output stream.
     *
     * @param out the output stream
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("typeAdvertisement");
        jg.writeStartObject();

        jg.writeStringField("idType", String.valueOf(idType));
        jg.writeStringField("type", type);

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    /**
     * Creates a {@code TypeAdvertisement} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code TypeAdvertisement} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static TypeAdvertisement fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        int jidType=-1;
        String jtype=null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "typeAdvertisement".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no advertisement object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "idType":
                        jp.nextToken();
                        jidType = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "type":
                        jp.nextToken();
                        jtype = jp.getValueAsString();
                        break;
                }
            }
        }

        return new TypeAdvertisement(jidType, jtype);
    }
}
