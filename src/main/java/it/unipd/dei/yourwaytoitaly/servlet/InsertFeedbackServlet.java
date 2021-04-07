package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.FeedbackDAO;
import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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

        // The user must give these parameters



        int idAdvertisement = 0;    // TODO: This is set by the DB
        String emailTourist=""; // This can be get by the servlet
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        int rate;
        String text;

        Feedback feedback  = null;
        Message m = null;

        try{

            // activate a session to keep the user data
            HttpSession session = req.getSession();

            // insert in the session the email
            emailTourist = session.getAttribute("email").toString();

            //TODO: Ottenere l'ID dell'annuncio che si recensisce
            rate = Integer.parseInt(req.getParameter("rate"));
            text = req.getParameter("text_f");

            // creates a new feedback from the request parameters
            feedback = new Feedback(
                    emailTourist,
                    0,
                    rate,
                    text,
                    date
            );

            // updates the booking
            /*feedback =
                    new CreateFeedbackDatabase(getDataSource().getConnection(), feedback)
                            .createFeedback();*/
            feedback = FeedbackDAO.createFeedback(feedback);
            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */
            m = new Message("Feedback successfully completed. IDs: "+emailTourist +" and "+idAdvertisement);


            // Show the booking just created in a web page
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

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