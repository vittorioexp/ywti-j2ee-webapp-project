package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.FeedbackDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

/**
 * Servlet class to create a new feedback
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public final class InsertFeedbackServlet extends AbstractDatabaseServlet {

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

        int idAdvertisement = 0;
        String emailTourist="";
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        // The user must give these parameters
        int rate;
        String text="";

        try{
            emailTourist = LoginServlet.getUserEmail(req);

            if (emailTourist.equals("")) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getErrorCode(),"User must be logged in.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            // check if the user already left a feedback for this advertisement
            Feedback feedback = FeedbackDAO.searchFeedback(emailTourist,idAdvertisement);
            if (feedback!=null) {
                ErrorCode ec = ErrorCode.FEEDBACK_ALREADY_DONE;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getErrorCode(),"Feedback already inserted.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            Advertisement  advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);
            Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

            // check if the user booked this advertisement and
            // check if the current date is bigger than the dateStart of the advertisement
            if (booking == null || currentDate.compareTo(advertisement.getDateStart())<0) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getErrorCode(),"The event has yet to begin");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            rate = Integer.parseInt(req.getParameter("rateFeedback"));
            text = req.getParameter("textFeedback");

            if (rate<1 || rate >5) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getErrorCode(), "Rate not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (text==null) {
                text="";
            }

            // creates a new feedback from the request parameters
            feedback = new Feedback(
                    emailTourist,
                    idAdvertisement,
                    rate,
                    text,
                    date
            );

            feedback = FeedbackDAO.createFeedback(feedback);


            Message success = new Message("Successfully left a feedback!");
            req.setAttribute("message", success);
            res.setStatus(HttpServletResponse.SC_OK);
            res.sendRedirect(req.getContextPath() + "/advertisement/" + idAdvertisement);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getErrorCode(), "Cannot create the feedback.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}