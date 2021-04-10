package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.FeedbackDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.User;
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
        //TODO: Controllare se l'utente ha prenotato quell'annuncio, e se il giorni corrente è maggiore di quello dell'annuncio (date_start)
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

            rate = Integer.parseInt(req.getParameter("rate"));
            text = req.getParameter("text_f");

            if (rate<1 || rate >5) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input rate is not valid. ",
                        ec.getErrorCode(), "Rate is " + rate);
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
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

            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);

            req.setAttribute("advertisement",advertisement);
            // Show the booking just created in a web page
            req.getRequestDispatcher("/advertisement/"+idAdvertisement).forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the feedback. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}