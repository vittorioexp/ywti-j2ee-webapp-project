package it.unipd.dei.yourwaytoitaly.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

/**
 * Class to define the objects which represents the search parameters
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchParameters extends Resource {
    private final int idType;
    private final int idCity;
    private final Date dateStart;

    public SearchParameters(int idType, int idCity, Date dateStart) {
        this.idType = idType;
        this.idCity = idCity;
        this.dateStart = dateStart;
    }

    public int getIdType() {
        return idType;
    }

    public int getIdCity() {
        return idCity;
    }

    public Date getDateStart() {
        return dateStart;
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
        jg.writeFieldName("searchParameters");
        jg.writeStartObject();

        jg.writeStringField("idType", String.valueOf(idType));
        jg.writeStringField("idCity", String.valueOf(idCity));
        jg.writeStringField("dateStart", String.valueOf(dateStart));

        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();

    }

    /**
     * Creates a {@code SearchParameters} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code SearchParameters} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static SearchParameters fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        int jidType=-1;
        int jidCity=-1;
        Date jdateStart=null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "searchParameters".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no searchParameters object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "idType":
                        jp.nextToken();
                        jidType = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "idCity":
                        jp.nextToken();
                        jidCity = Integer.parseInt(jp.getValueAsString());
                        break;
                    case "dateStart":
                        jp.nextToken();
                        jdateStart = Date.valueOf(jp.getValueAsString());
                        break;
                }
            }
        }

        return new SearchParameters(jidType, jidCity, jdateStart);

    }
}