package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for searching Bookings inside the database and returning them in a List:
 * receives the id related to the user who made the request to show the Bookings related to his
 * account (reqIdUser) and returns a List containing all the Bookings with the same idUser and sorted by dateTime
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */

public final class SearchBookingByUserDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT DATE_B, TIME_B, num_booking, state, ID_advertisement\n" +
            "\tFROM BOOKING\n" +
            "\tWHERE email_t = ? ;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    private final String reqIdTourist;

    /**
     * Creates a new object for searching advertisement by some search parameters.
     *  @param con
     *              the connection to the database.
     * @param reqIdTourist
     *
     */
    public SearchBookingByUserDatabase(final Connection con, final String reqIdTourist) {
        this.con = con;
        this.reqIdTourist = reqIdTourist;
    }

    /**
     * Searches bookings by tourist.
     *
     * @return a list of {@code Booking} objects matching with the tourist email.
     *
     * @throws SQLException
     *             if any error occurs while searching for bookings.
     */

    public List<Booking> SearchBookingByUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Booking> bookings = new ArrayList<Booking>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqIdTourist);


            rs = pstmt.executeQuery();

            while (rs.next()) {

                bookings.add(new Booking(
                        rs.getString("email_t"),
                        rs.getInt("ID_advertisement"),
                        rs.getDate("date_b"),
                        rs.getTime("time_b"),
                        rs.getInt("num_booking"),
                        rs.getString("state")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return bookings;
    }
}