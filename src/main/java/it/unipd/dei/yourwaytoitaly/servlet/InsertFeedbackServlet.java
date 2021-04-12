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

        Feedback feedback  = null;

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

            emailTourist = u.getEmail();
            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));
            Advertisement  advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);

            Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

            // check if the user booked this advertisement and
            // check if the current date is bigger than the dateStart of the advertisement
            if (booking == null || currentDate.compareTo(advertisement.getDateStart())<0) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message("User cannot insert a feedback.",
                        ec.getErrorCode(),"User cannot insert a feedback about this booking.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/advertisement/" + idAdvertisement).forward(req, res);
            }

            rate = Integer.parseInt(req.getParameter("rate"));
            text = req.getParameter("text_f");

            if (rate<1 || rate >5) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input rate is not valid. ",
                        ec.getErrorCode(), "Rate is " + rate);
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/include/show-message.jsp").forward(req, res);
            }

            // creates a new feedback from the request parameters
            feedback = new Feedback(
                    emailTourist,
                    0,
                    rate,
                    text,
                    date
            );

            feedback = FeedbackDAO.createFeedback(feedback);

            // Show the advertisement
            req.getRequestDispatcher("/advertisement/"+idAdvertisement).forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the feedback. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/include/show-message.jsp").forward(req, res);
        }
    }
}