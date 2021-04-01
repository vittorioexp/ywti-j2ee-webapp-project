package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Booking;
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
    private static final String STATEMENT =
            "INSERT INTO Booking (email_t, ID_Advertisement, date_b, time_b, num_booking, state) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
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
            pstmt.setString(1, booking.getEmailTourist());
            pstmt.setInt(2, booking.getIdAdvertisement());
            pstmt.setDate(3, booking.getDate());
            pstmt.setTime(4, booking.getTime());
            pstmt.setInt(5, booking.getNumBooking());
            pstmt.setString(6, booking.getState());

            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}