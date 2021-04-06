package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateBookingDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchAdvertisementById;
import it.unipd.dei.yourwaytoitaly.database.SearchBookingByAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

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
        int itemBooked=0;
        int NumTotItem=0;


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
            NumTotItem = new SearchAdvertisementById(getDataSource().getConnection(), idAdvertisement).searchAdvertisement().getNumTotItem();
            List<Booking> bookingList = new SearchBookingByAdvertisementDatabase(getDataSource().getConnection(), idAdvertisement).searchBookingByAdvertisement();
            for(Booking b: bookingList){
                itemBooked+=b.getNumBooking();
            }
            if(numBooking<=0 || numBooking>NumTotItem-itemBooked){
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
            booking =
                    new CreateBookingDatabase(getDataSource().getConnection(), booking)
                            .createBooking();

            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */
            m = new Message("Booking successfully completed. IDs: "+emailTourist +" and "+idAdvertisement);


            // Show the booking just created in a web page
            req.getRequestDispatcher("ShowBookingServlet").forward(req, res);

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