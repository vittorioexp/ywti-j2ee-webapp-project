package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.FeedbackDAO;
import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        int idAdvertisement = 0; // TODO: get this somehow
        String emailTourist="";
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        // The user must give these parameters
        int rate;
        String text;

        Feedback feedback  = null;

        try{
            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email")==null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // insert in the session the email
            emailTourist = session.getAttribute("email").toString();

            rate = Integer.parseInt(req.getParameter("rate"));
            text = req.getParameter("text_f");

            if (rate<1 || rate >5) {
                Message m = new Message("Input rate is not valid. ",
                        "E100", "Rate is " + rate);
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
            if (text==null || text.equals("") || text.length()<1) {
                Message m = new Message("Feedback text not valid. ",
                        "E100", "");
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
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

            // Show the booking just created in a web page
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

        } catch (Exception ex) {
            Message m = new Message("Cannot create the feedback. ",
                    "E100", ex.getMessage());
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}