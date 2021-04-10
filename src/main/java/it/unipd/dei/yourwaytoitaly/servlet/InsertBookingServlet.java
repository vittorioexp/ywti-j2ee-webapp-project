package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


        int idAdvertisement = 0;
        String emailTourist="";     // This can be get from the session
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        String state = "PROCESSING";
        int numTotItem=0;

        Booking booking  = null;

        try{

            // check if a session is valid
            User u = new SessionCheckServlet(req, res).getUser();
            if (u == null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // check if the email of the session is equal to emailCompany
            emailTourist = u.getEmail();

            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            // Get how many items the user wants to book
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            // Get the booking date
            Date bDate = Date.valueOf(req.getParameter("numBooking"));

            if(numBooking<=0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"The number of item is not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/advertisement/" + idAdvertisement).forward(req, res);
            }

            // Check if dateStart <= bookingDate <= dateEnd
            Advertisement adv = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if (adv.getDateEnd().compareTo(bDate)<0 || bDate.compareTo(adv.getDateStart())<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"The booking date is not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/advertisement/" + idAdvertisement).forward(req, res);
            }

            // Get the number of items available
            numTotItem = adv.getNumTotItem();

            // Get the bookings relative to this advertisement
            List<Booking> bookingList = BookingDAO.searchBookingByAdvertisement(idAdvertisement);

            // Get the number of items already booked
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
                req.getRequestDispatcher("/advertisement/" + idAdvertisement).forward(req, res);
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

            // Show the booking just created in a web page
            req.getRequestDispatcher("/user/profile/").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the booking. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);
        }
    }
}