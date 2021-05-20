package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

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
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public final class InsertBookingServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * Creates a booking
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        // The user must give these parameters
        int numBooking;


        int idAdvertisement = 0;
        String emailTourist="";     // This can be get from the session
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        String state = "PROCESSING";
        int numTotItem=0;

        try{

            emailTourist = LoginServlet.getUserEmail(req);
            User user = UserDAO.searchUserByEmail(emailTourist);
            if(user instanceof Company){
                ErrorCode ec = ErrorCode.USER_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User not allowed.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (emailTourist.equals("")) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User not logged in.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            // check if the tourist already booked
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);
            if (booking!=null) {
                ErrorCode ec = ErrorCode.BOOKING_ALREADY_DONE;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Booking already done.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Get how many items the user wants to book
            numBooking = Integer.parseInt(req.getParameter("numBooking"));

            if(numBooking<=0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Invalid number of items booked.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Check if bookingDate <= dateEnd
            Advertisement adv = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if (adv.getDateEnd().compareTo(date)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"You can't book this: The event has ended on " + adv.getDateEnd());
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Get the number of items available
            numTotItem = adv.getNumTotItem();

            if(numBooking > numTotItem){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"The number of item is too big.");
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

            try {
                booking = BookingDAO.createBooking(booking);
            } catch (SQLException ex) {
                ErrorCode ec = ErrorCode.BOOKING_ALREADY_DONE;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"You have already booked this item.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            adv = new Advertisement(
                    idAdvertisement,
                    adv.getTitle(),
                    adv.getDescription(),
                    adv.getScore(),
                    adv.getPrice(),
                    adv.getNumTotItem()-numBooking,
                    adv.getDateStart(),
                    adv.getDateEnd(),
                    adv.getTimeStart(),
                    adv.getTimeEnd(),
                    adv.getEmailCompany(),
                    adv.getIdType()
            );

            AdvertisementDAO.editAdvertisement(adv);

            // Calculate the score
            int userScore = UserDAO.getUserScore(emailTourist);

            Message message = new Message("Congratulation! Booking is successful!");

            // if (userScore % 7 == 0), give the user a discount of 5% on this booking
            // if (userScore % 17 == 0), give the user a discount of 10% on this booking
            if (userScore % 7 == 0) {
                message = new Message("Congratulation, booking is successful!" + "\n" + "You have unlocked a 5% discount on this booking!");
            } else if (userScore % 17 == 0) {
                message = new Message("Congratulation, booking is successful!" + "\n" + "You have unlocked a 10% discount on this booking!");
            }

            res.setStatus(HttpServletResponse.SC_OK);
            message.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot create the booking: " + ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}