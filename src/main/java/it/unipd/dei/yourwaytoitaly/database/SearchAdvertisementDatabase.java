package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for searching Advertisements inside the database and returning them in a List:
 * receives the search parameters requested by the user for an advertisement (reqIdCity + reqIdType + reqDate,
 * all in an appropriate format) and returns a List containing all the Advertisement compatible with what the user
 * requested. A Advertisement (having idCity, idType, dateStart and dateEnd attributes) present in the DB is
 * compatible with the one requested if:
 * reqIdCity == idCity AND reqIdType == idType AND dateStart <= reqDate <= dateEnd.
 * If there are no compatible Advertisement, it returns null.
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchAdvertisementDatabase {
    private static final String STATEMENT = ""; //TODO: query needed
    private final Connection con;
    private final int reqIdCity;
    private final int reqIdType;
    private final Timestamp reqDate:

    public SearchAdvertisementDatabase(final Connection con, final int reqIdCity, final int reqIdType,
                                       final Timestamp reqDate) {
        this.con = con;
        this.reqIdCity = reqIdCity;
        this.reqIdType = reqIdType;
        this.reqDate = reqDate;
    }
    public List<Advertisement> SearchAdvertisement() throws SQLException {
        //TODO: write the function body
        return null;
    }
}