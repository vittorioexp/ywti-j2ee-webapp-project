package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

/**
 * Class to define the objects which represents the Feedback in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Feedback extends Resource{

    private final String emailTourist;
    private final int idAdvertisement;
    private final int rate;
    private final String text;
    private final Date date;

    public Feedback(final String emailTourist, final int idAdvertisement,
                    final int rate, final String text, final Date date) {
        this.emailTourist = emailTourist;
        this.idAdvertisement = idAdvertisement;
        this.rate = rate;
        this.text = text;
        this.date = date;
    }

    public final String getEmailTourist() {
        return emailTourist;
    }

    public final int getIdAdvertisement() {
        return idAdvertisement;
    }

    public final int getRate() {
        return rate;
    }

    public final String getText() {
        return text;
    }

    public final Date getDate() {
        return date;
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
        jg.writeFieldName("feedback");
        jg.writeStartObject();

        jg.writeStringField("emailTourist", emailTourist);
        jg.writeStringField("idAdvertisement", String.valueOf(idAdvertisement));
        jg.writeStringField("rate", String.valueOf(rate));
        jg.writeStringField("text", text);
        jg.writeStringField("date", date.toString());

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    /**
     * Creates a {@code Feedback} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Feedback} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Feedback fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        String jemailTourist=null;
        int jidAdvertisement=-1;
        int jrate=-1;
        String jtext=null;
        Date jdate=null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "feedback".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no feedback object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "emailTourist":
                        jp.nextToken();
                        jemailTourist = jp.getValueAsString();
                        break;
                    case "idAdvertisement":
                        jp.nextToken();
                        jidAdvertisement = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "rate":
                        jp.nextToken();
                        jrate = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "text":
                        jp.nextToken();
                        jtext = jp.getValueAsString();
                        break;
                    case "date":
                        jp.nextToken();
                        jdate = Date.valueOf(jp.getValueAsString());
                        break;
                }
            }
        }

        return new Feedback(jemailTourist, jidAdvertisement, jrate, jtext,jdate);
    }
}
