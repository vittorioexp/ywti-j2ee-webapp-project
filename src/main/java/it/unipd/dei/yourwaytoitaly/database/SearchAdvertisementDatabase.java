package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for searching Advertisements inside the database and returning them in a List:
 * receives the search parameters requested by the user for an advertisement (reqIdCity + reqIdType + reqDate + reqTime,
 * all in an appropriate format) and returns a List containing all the Advertisement compatible with what the user
 * requested. If there are no compatible Advertisement, it returns null.
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchAdvertisementDatabase {
    private static final String STATEMENT = ""; //TODO: query needed
    private final Connection con;
    private final int reqIdCity;
    private final int reqIdType;
    private final Date reqDate;
    private final Time reqTime;

    public SearchAdvertisementDatabase(final Connection con, final int reqIdCity, final int reqIdType,
                                       final Date reqDate, final Time reqTime = null) {
        this.con = con;
        this.reqIdCity = reqIdCity;
        this.reqIdType = reqIdType;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
    }
    public List<Advertisement> SearchAdvertisement() throws SQLException {
        //TODO: write the function body
        return null;
    }
}