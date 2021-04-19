package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static it.unipd.dei.yourwaytoitaly.resource.Resource.JSON_FACTORY;

/**
 * Class to define the objects which represents the Image in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Image {
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
        // TODO: write function body
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        /*
        jg.writeStartObject();
        jg.writeFieldName("advertisement");
        jg.writeStartObject();

        jg.writeNumberField("idAdvertisement", idAdvertisement);
        jg.writeStringField("title", title);
        jg.writeStringField("description", description);
        jg.writeNumberField("score", score);
        jg.writeNumberField("price", price);
        jg.writeNumberField("numTotItem", numTotItem);
        jg.writeStringField("dateStart", dateStart.toString());
        jg.writeStringField("dateEnd", dateEnd.toString());
        jg.writeStringField("timeStart", timeStart.toString());
        jg.writeStringField("timeEnd", timeEnd.toString());
        jg.writeStringField("emailCompany", emailCompany);
        jg.writeNumberField("idType", idType);

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
        */

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
        /*
        // the fields read from JSON
        int jidAdvertisement=-1;
        String jtitle=null;
        String jdescription=null;
        int jscore=-1;
        int jprice=-1;
        int jnumTotItem=-1;
        Date jdateStart=null;
        Date jdateEnd=null;
        Time jtimeStart=null;
        Time jtimeEnd=null;
        String jemailCompany=null;
        int jidType=-1;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "advertisement".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no advertisement object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "idAdvertisement":
                        jp.nextToken();
                        jidAdvertisement = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "title":
                        jp.nextToken();
                        jtitle = jp.getValueAsString();
                        break;
                    case "description":
                        jp.nextToken();
                        jdescription = jp.getValueAsString();
                        break;
                    case "score":
                        jp.nextToken();
                        jscore = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "price":
                        jp.nextToken();
                        jprice = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "numTotItem":
                        jp.nextToken();
                        jnumTotItem = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "dateStart":
                        jp.nextToken();
                        jdateStart = Date.valueOf(jp.getValueAsString());
                        break;
                    case "dateEnd":
                        jp.nextToken();
                        jdateEnd = Date.valueOf(jp.getValueAsString());
                        break;
                    case "timeStart":
                        jp.nextToken();
                        jtimeStart = Time.valueOf(jp.getValueAsString());
                        break;
                    case "timeEnd":
                        jp.nextToken();
                        jtimeEnd = Time.valueOf(jp.getValueAsString());
                        break;
                    case "emailCompany":
                        jp.nextToken();
                        jemailCompany = jp.getValueAsString();
                        break;
                    case "idType":
                        jp.nextToken();
                        jidType = Integer.parseInt(jp.getValueAsString());
                        break;

                }
            }
        }

        return new Advertisement(jidAdvertisement, jtitle, jdescription, jscore,jprice,jnumTotItem,jdateStart,jdateEnd,jtimeStart,jtimeEnd,jemailCompany,jidType);
    */
        return null;
    }
}