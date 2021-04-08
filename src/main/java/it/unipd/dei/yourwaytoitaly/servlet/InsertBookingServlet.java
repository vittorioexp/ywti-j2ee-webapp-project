package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
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


        int idAdvertisement = 0;    // TODO: get this somehow
        String emailTourist="";     // This can be get from the session
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        String state = "PROCESSING";
        int numTotItem=0;

        Booking booking  = null;

        try{

            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email")==null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // insert in the session the email
            emailTourist = session.getAttribute("email").toString();

            // Get how many items the user wants to book
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            if(numBooking<=0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"The number of item is not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            // Get the number of items available
            numTotItem = AdvertisementDAO.searchAdvertisement(idAdvertisement).getNumTotItem();

            // Get the bookings relative to this advertisement
            List<Booking> bookingList = BookingDAO.searchBookingByAdvertisement(idAdvertisement);

            int itemBooked=0;
            for(Booking b: bookingList){
                itemBooked += b.getNumBooking();
            }
            if(numBooking > numTotItem - itemBooked){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"The number of item is too big!");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
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

            booking = BookingDAO.createBooking(booking);

            int totalUserScore = UserDAO.searchUserScore(emailTourist);

            // Show the booking just created in a web page
            req.getRequestDispatcher("/jsp/show-result-booking.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the booking. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}