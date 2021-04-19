package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class to define the objects which represents the Image in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Image extends Resource {
    private final int idImage;
    private final String path;
    private final String description;
    private final int idAdvertisement;

    public Image(final int idImage, final String path, final String description, final int idAdvertisement) {
        this.idImage = idImage;
        this.path = path;
        this.description = description;
        this.idAdvertisement = idAdvertisement;
    }
    public final int getIdImage() {
        return idImage;
    }
    public final String getPath() {
        return path;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final String getDescription() {
        return description;
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
        jg.writeFieldName("image");
        jg.writeStartObject();

        jg.writeStringField("idImage", String.valueOf(idImage));
        jg.writeStringField("path", path);
        jg.writeStringField("description", description);
        jg.writeStringField("idAdvertisement", String.valueOf(idAdvertisement));

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();

    }

    /**
     * Creates a {@code Image} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Image} created from the JSON representation.
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