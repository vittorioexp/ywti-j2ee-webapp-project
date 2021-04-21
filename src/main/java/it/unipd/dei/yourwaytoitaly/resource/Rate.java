package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class to define the rate of an advertisement
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */
public final class Rate extends Resource{
    private final int rate;

    public Rate(int rate) {
        this.rate=rate;
    }

    public int getRate() {
        return rate;
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
        jg.writeFieldName("rate");
        jg.writeStartObject();

        jg.writeNumberField("rate", rate);

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();

    }

    /**
     * Creates a {@code Rate} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Rate} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Image fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        int jidImage=-1;
        String jpath=null;
        String jdescription=null;
        int jidAdvertisement=-1;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "image".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no image object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "idImage":
                        jp.nextToken();
                        jidImage = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "path":
                        jp.nextToken();
                        jpath = jp.getValueAsString();
                        break;
                    case "description":
                        jp.nextToken();
                        jdescription = jp.getValueAsString();
                        break;
                    case "idAdvertisement":
                        jp.nextToken();
                        jidAdvertisement = Integer.parseInt(jp.getValueAsString());
                        break;
                }
            }
        }

        return new Image(jidImage, jpath, jdescription, jidAdvertisement);

    }
}
