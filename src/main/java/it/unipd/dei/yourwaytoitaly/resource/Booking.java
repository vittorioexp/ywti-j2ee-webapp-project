package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;

/**
 * Class to define the objects which represents the Booking in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Booking extends Resource{
    private final String emailTourist;
    private final int idAdvertisement;
    private final Date date;
    private final Time time;
    private final int numBooking;
    private final String state;

    public Booking(final String emailTourist, final int idAdvertisement, final Date date, final Time time,
                   final int numBooking, final String state) {
        this.emailTourist = emailTourist;
        this.idAdvertisement = idAdvertisement;
        this.date = date;
        this.time = time;
        this.numBooking = numBooking;
        this.state = state;
    }

    public final String getEmailTourist() {
        return emailTourist;
    }
    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final Date getDate() {
        return date;
    }
    public final Time getTime() {
        return time;
    }
    public final int getNumBooking() {
        return numBooking;
    }
    public final String getState() {
        return state;
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
        jg.writeFieldName("booking");
        jg.writeStartObject();

        jg.writeStringField("emailTourist", emailTourist);
        jg.writeStringField("idAdvertisement", String.valueOf(idAdvertisement));
        jg.writeStringField("date", date.toString());
        jg.writeStringField("time", time.toString());
        jg.writeStringField("numBooking", String.valueOf(numBooking));
        jg.writeStringField("state", state);

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    /**
     * Creates a {@code Booking} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Booking} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Booking fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        String jemailTourist=null;
        int jidAdvertisement=-1;
        Date jdate=null;
        Time jtime=null;
        int jnumBooking=-1;
        String jstate=null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "booking".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no booking object found.");
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
                    case "date":
                        jp.nextToken();
                        jdate = Date.valueOf(jp.getValueAsString());
                        break;
                    case "time":
                        jp.nextToken();
                        jtime = Time.valueOf(jp.getValueAsString());
                        break;
                    case "numBooking":
                        jp.nextToken();
                        jnumBooking = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "state":
                        jp.nextToken();
                        jstate = jp.getValueAsString();
                        break;

                }
            }
        }

        return new Booking(jemailTourist, jidAdvertisement, jdate, jtime,jnumBooking,jstate);

    }

}