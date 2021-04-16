package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
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

        try{
            // TODO: check if the email is of a tourist
            emailTourist = LoginServlet.getUserEmail(req);

            if (emailTourist.equals("")) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message("Method not allowed: User not logged in.",
                        ec.getErrorCode(),"User must be logged in to create a booking");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            // check if the tourist already booked
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);
            if (booking!=null) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message("Method not allowed: User already booked.",
                        ec.getErrorCode(),"User already booked!");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Get how many items the user wants to book
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            if(numBooking<=0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"The number of item is not valid");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Check if bookingDate <= dateEnd
            Advertisement adv = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if (adv.getDateEnd().compareTo(date)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("The advertisement is expired.",
                        ec.getErrorCode(),"You cannot book an advertisement which is expired");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
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
                m.toJSON(res.getOutputStream());
                return;
            }

            state="SUCCESSFUL";

            // creates a new booking from the request parameters
            booking = new Booking(
                    emailTourist,
                    idAdvertisement,
                    date,
                    time,
                    numBooking,
                    state
            );

            booking = BookingDAO.createBooking(booking);

            Message success = new Message("Successfully booked!");
            req.setAttribute("message", success);
            res.setStatus(HttpServletResponse.SC_OK);
            res.sendRedirect(req.getContextPath() + "/user/profile");

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the booking. ",
                    ec.getErrorCode(), ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}