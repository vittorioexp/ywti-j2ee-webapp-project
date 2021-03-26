package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Booking;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * Class for searching Bookings inside the database and returning them in a List:
 * receives the id related to the user who made the request to show the Bookings related to his
 * account (reqIdUser) and returns a List containing all the Bookings with the same idUser and sorted by dateTime
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchBookingByDateDatabase {
    private static final String STATEMENT = ""; //TODO: query needed
    private final Connection con;
    private final int reqIduser;

    public SearchBookingByDateDatabase(final Connection con, final int reqIduser) {
        this.con = con;
        this.reqIduser = reqIduser;
    }

    public List<Booking> SearchBookingByDate() throws SQLException {
        //TODO: write the function body
        return null;
    }
}