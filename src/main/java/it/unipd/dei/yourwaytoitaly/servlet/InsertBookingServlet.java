package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateBookingDatabase;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

/**
 * Servlet class to create a new booking
 *
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class InsertBookingServlet extends AbstractDatabaseServlet {

    /**
     * Create a booking
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // The user must give these parameters
        int numBooking;


        int idAdvertisement;    // This is set by the DB
        String emailTourist=""; // TODO: This can be get by the servlet
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        String state = "PROCESSING";

        Booking booking  = null;
        Message m = null;

        try{
            // retrieves the request parameters
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            //TODO: Here make some controls of the inserted data
            //...

            // creates a new advertisement from the request parameters
            booking = new Booking(
                    emailTourist,
                    0,
                    date,
                    time,
                    numBooking,
                    state
            );

            // updates the booking
            booking =
                    new CreateBookingDatabase(getDataSource().getConnection(), booking)
                            .createBooking();

            m = new Message(String.format("Advertisement %s successfully created. ID:",
                    booking.getEmailTourist()));

            // Show the booking just created in a web page


        } catch (NumberFormatException ex) {
            m = new Message("Cannot create the booking. " +
                    "Invalid input parameters",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot create the booking.: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        }
    }
}