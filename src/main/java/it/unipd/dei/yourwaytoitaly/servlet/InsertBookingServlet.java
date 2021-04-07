package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.naming.NamingException;
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
 * Servlet class to create a new booking
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
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


        int idAdvertisement = 0;    // This is set by the DB
        String emailTourist=""; // TODO: This can be get by the servlet
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        String state = "PROCESSING";
        int itemBooked=0;
        int numTotItem=0;


        Booking booking  = null;
        Message m = null;

        try{

            // activate a session to keep the user data
            HttpSession session = req.getSession();

            // insert in the session the email
            emailTourist = session.getAttribute("email").toString();
            // retrieves the request parameters
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            //TODO: Ottenere l'ID dell'annuncio che l'utente ha selezionato
            //NumTotItem = new SearchAdvertisementById(getDataSource().getConnection(), idAdvertisement).searchAdvertisement().getNumTotItem();
            //List<Booking> bookingList = new SearchBookingByAdvertisementDatabase(getDataSource().getConnection(), idAdvertisement).searchBookingByAdvertisement();

            numTotItem = AdvertisementDAO.searchAdvertisement(idAdvertisement).getNumTotItem();
            List<Booking> bookingList = BookingDAO.searchBookingByAdvertisement(idAdvertisement);

            for(Booking b: bookingList){
                itemBooked+=b.getNumBooking();
            }
            if(numBooking<=0 || numBooking>numTotItem-itemBooked){
                //TODO: Sistemare le righe commentate
                //ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                //res.setStatus(ec.getHTTPCode());
                Message msg = new Message("Input value not valid.",
                        "E4","Number of item not valid.");
                //req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }

            state="SUCCESSFUL";
            // creates a new booking from the request parameters
            booking = new Booking(
                    emailTourist,
                    0,
                    date,
                    time,
                    numBooking,
                    state
            );

            // updates the booking
            /*booking =
                    new CreateBookingDatabase(getDataSource().getConnection(), booking)
                            .createBooking();*/
            booking = BookingDAO.createBooking(booking);
            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */

            //int totalUserScore = new SearchUserScoreById(getDataSource().getConnection(), emailTourist).searchUserScore();
            int totalUserScore = UserDAO.searchUserScore(emailTourist);

            m = new Message("Booking successfully completed. User total score: "+ totalUserScore + ", IDs: "+emailTourist +" and "+idAdvertisement);

            // Show the booking just created in a web page
            req.getRequestDispatcher("/jsp/show-result-booking.jsp").forward(req, res);

        } catch (NumberFormatException ex) {
            m = new Message("Cannot create the booking. " +
                    "Invalid input parameters",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot create the booking.: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        } catch (NamingException e) {
            // TODO fix
            e.printStackTrace();
        }
    }
}