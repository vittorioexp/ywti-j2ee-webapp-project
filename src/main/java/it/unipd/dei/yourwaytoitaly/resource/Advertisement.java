package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.json.JSONObject;

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

    public final JSONObject toJSON() throws IOException {
        //final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        JSONObject jg = new JSONObject();

        jg.put("idAdvertisement", idAdvertisement);
        jg.put("title", title);
        jg.put("description", description);
        jg.put("score", score);
        jg.put("price", price);
        jg.put("numTotItem", numTotItem);
        jg.put("dateStart", dateStart.toString());
        jg.put("dateEnd", dateEnd.toString());
        jg.put("timeStart", timeStart.toString());
        jg.put("timeEnd", timeEnd.toString());
        jg.put("emailCompany", emailCompany);
        jg.put("idType", idType);

        return jg;
    }


    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

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
                        jidAdvertisement = jp.getIntValue();
                        break;
                    case "title":
                        jp.nextToken();
                        jtitle = jp.getText();
                        break;
                    case "description":
                        jp.nextToken();
                        jdescription = jp.getValueAsString();
                        break;
                    case "score":
                        jp.nextToken();
                        jscore = jp.getIntValue();
                        break;
                    case "price":
                        jp.nextToken();
                        jprice = jp.getIntValue();
                        break;
                    case "numTotItem":
                        jp.nextToken();
                        jnumTotItem = jp.getIntValue();
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
                        jidType = jp.getIntValue();
                        break;

                }
            }
        }

        return new Advertisement(jidAdvertisement, jtitle, jdescription, jscore,jprice,jnumTotItem,jdateStart,jdateEnd,jtimeStart,jtimeEnd,jemailCompany,jidType);
    }




}
