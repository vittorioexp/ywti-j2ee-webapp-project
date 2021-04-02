package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class for inserting a Booking inside the database
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */

public final class CreateBookingDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "INSERT INTO YWTI.Booking (email_t, ID_Advertisement, date_b, time_b, num_booking, state) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING *;";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The booking to create
     */
    private final Booking booking;

    /**
     * Creates a new object for creating bookings.
     *
     * @param con
     *            the connection to the database.
     * @param booking
     *            the booking to create.
     */
    public CreateBookingDatabase(final Connection con, final Booking booking) {
        this.con = con;
        this.booking = booking;
    }

    /**
     * Creates a new booking.
     *
     * @return the just created booking
     *
     * @throws SQLException
     *             if any error occurs while creating bookings.
     */
    public Booking createBooking() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        Booking b = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, booking.getEmailTourist());
            pstmt.setInt(2, booking.getIdAdvertisement());
            pstmt.setDate(3, booking.getDate());
            pstmt.setTime(4, booking.getTime());
            pstmt.setInt(5, booking.getNumBooking());
            pstmt.setString(6, booking.getState());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                b = new Booking(
                        rs.getString("email_t"),
                        rs.getInt("ID_advertisement"),
                        rs.getDate("date_b"),
                        rs.getTime("time_b"),
                        rs.getInt("num_booking"),
                        rs.getString("state"));
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
        return b;
    }
}