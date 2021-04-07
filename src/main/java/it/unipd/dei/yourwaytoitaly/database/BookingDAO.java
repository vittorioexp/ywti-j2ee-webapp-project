package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for:
 * - inserting a Booking
 * - editing some parameters of a Booking
 * - searching and returning some Bookings by ID_ADVERTISEMENT
 * - searching and returning some Bookings by EMAIL_T
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class BookingDAO extends AbstractDAO{

    /**
     * Inserts a new booking.
     *
     * @return the just created booking
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static Booking createBooking(Booking booking) throws SQLException, NamingException {
        final String STATEMENT =
                "INSERT INTO YWTI.Booking (email_t, ID_Advertisement, date_b, time_b, num_booking, state) " +
                        "VALUES (?, ?, ?, ?, ?, ?) RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return b;
    }

    /**
     * Edits some parameters of a Booking
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static void editBooking(Booking booking) throws SQLException, NamingException {
        final String STATEMENT =
                "UPDATE YWTI.BOOKING SET state = 'DELETED' WHERE email_t = ? AND ID_advertisement = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, booking.getEmailTourist());
            pstmt.setInt(2, booking.getIdAdvertisement());

            rs = pstmt.executeQuery();

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
    }


    /**
     * searches and returns some Bookings by ID_ADVERTISEMENT
     *
     * @return a list of {@code Booking} objects matching with the Id of the advertisement.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static List<Booking> searchBookingByAdvertisement(int reqIdAdvertisement)
            throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT DATE_B, TIME_B, num_booking, state, email_t " +
                        "FROM YWTI.BOOKING\n" +
                        "WHERE ID_advertisement = ? ;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Booking> bookings = new ArrayList<Booking>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdAdvertisement);

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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return bookings;
    }


    /**
     * searches and returns some Bookings by EMAIL_T
     *
     * @return a list of {@code Booking} objects matching with the email of the tourist
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static List<Booking> searchBookingByUser(String reqEmailTourist) throws SQLException, NamingException {
        final String STATEMENT = "SELECT DATE_B, TIME_B, num_booking, state, ID_advertisement " +
                "FROM BOOKING " +
                "WHERE email_t = ? ;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Booking> bookings = new ArrayList<Booking>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqEmailTourist);

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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return bookings;
    }
}
