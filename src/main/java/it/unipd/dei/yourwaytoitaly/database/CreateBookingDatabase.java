package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for inserting a Booking inside the database
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateBookingDatabase {
    private static final String STATEMENT = ""; //TODO: query is needed here
    private final Connection con;
    private final Booking booking;
    public CreateBookingDatabase(final Connection con, final Booking booking) {
        this.con = con;
        this.booking = booking;
    }
    public void createBooking() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, booking.getIdUser());
            pstmt.setInt(2, booking.getIdAdvertisement());
            pstmt.setTimestamp(3, booking.getDateTime());
            pstmt.setInt(4, booking.getNumBooking());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}