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
 * Class to define the objects which represents the Advertisement in the DB
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class Advertisement extends Resource{
    private final int idAdvertisement;
    private final String title;
    private final String description;
    private final int score;
    private final int price;
    private final int numTotItem;
    private final Date dateStart;
    private final Date dateEnd;
    private final Time timeStart;
    private final Time timeEnd;
    private final String emailCompany;
    private final int idType;

    public Advertisement(final int idAdvertisement, final String title, final String description, final int score, final int price,
                         final int numTotItem, final Date dateStart, final Date dateEnd, final Time timeStart,
                         final Time timeEnd, final String emailCompany, final int idType) {
        this.idAdvertisement = idAdvertisement;
        this.title = title;
        this.description = description;
        this.score = score;
        this.price = price;
        this.numTotItem = numTotItem;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.emailCompany = emailCompany;
        this.idType = idType;
    }

    public final int getIdAdvertisement() {
        return idAdvertisement;
    }
    public final String getTitle() { return title; }
    public final String getDescription() {
        return description;
    }
    public final int getScore() {
        return score;
    }
    public final int getPrice() {
        return price;
    }
    public final int getNumTotItem() {
        return numTotItem;
    }
    public final Date getDateStart() {
        return dateStart;
    }
    public final Date getDateEnd() {
        return dateEnd;
    }
    public final Time getTimeStart() {
        return timeStart;
    }
    public final Time getTimeEnd() {
        return timeEnd;
    }
    public final String getEmailCompany() {
        return emailCompany;
    }
    public final int getIdType() {
        return idType;
    }

    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();
        jg.writeFieldName("advertisement");
        jg.writeStartObject();
        jg.writeNumberField("idAdvertisement", idAdvertisement);

        jg.writeStringField("description", description);
        jg.writeNumberField("score", score);
        jg.writeNumberField("price", price);
        jg.writeNumberField("numTotItem", numTotItem);
        jg.writeDateField("dateStart", dateStart);
        jg.writeDateField("dateEnd", dateEnd);
        jg.writeTimeField("timeStart", timeStart);
        jg.writeTimeField("timeEnd", timeEnd);
        jg.writeStringField("emailCompany", emailCompany);

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    /**
     * Creates a {@code Advertisement} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Employee} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Advertisement fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        int idAdvertisement=-1;
        String title=null;
        String description=null;
        int score=-1;
        int price=-1;
        int numTotItem=-1;
        Date dateStart=null;
        Date dateEnd=null;
        Time timeStart=null;
        Time timeEnd=null;
        String emailCompany=null;
        int idType=-1;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "employee".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no employee object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "badge":
                        jp.nextToken();
                        jBadge = jp.getIntValue();
                        break;
                    case "surname":
                        jp.nextToken();
                        jSurname = jp.getText();
                        break;
                    case "age":
                        jp.nextToken();
                        jAge = jp.getIntValue();
                        break;
                    case "salary":
                        jp.nextToken();
                        jSalary = jp.getIntValue();
                        break;
                }
            }
        }

        return new Advertisement(jBadge, jSurname, jAge, jSalary);
    }
}
