package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateBookingDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchAdvertisementById;
import it.unipd.dei.yourwaytoitaly.database.SearchBookingByAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchUserScoreById;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Servlet class to edit a booking
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class EditBookingServlet extends AbstractDatabaseServlet{

    /**
     * Edit a booking
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

        int idAdvertisement;    // This is set by the DB
        String emailTourist=""; // TODO: This can be get by the servlet
        Date date = null;
        Time time = null;
        String state = null;
        int numBooking=0;

        Booking booking;
        Message m = null;

        try{

            // activate a session to keep the user data
            HttpSession session = req.getSession();

            // insert in the session the email
            emailTourist = session.getAttribute("email").toString();

            //TODO: Ottenere l'ID dell'annuncio che l'utente vuole eliminare

            booking = new Booking(
                    emailTourist,
                    0,
                    date,
                    time,
                    numBooking,
                    state
            );

            // delete the booking
            new CreateBookingDatabase(getDataSource().getConnection(), booking).deleteBooking(booking);

            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */

            int totalUserScore = new SearchUserScoreById(getDataSource().getConnection(), emailTourist).searchUserScore();

            m = new Message("Booking successfully deleted. User total score: "+ totalUserScore + ", IDs: "+emailTourist +" and "+idAdvertisement);

            // Show the list of booking
            req.getRequestDispatcher("/jsp/show-bookings-of-user.jsp").forward(req, res);

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
