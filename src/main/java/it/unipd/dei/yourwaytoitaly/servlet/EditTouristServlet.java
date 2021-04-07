package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Servlet class to edit the user profile
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class EditTouristServlet extends AbstractDatabaseServlet {
    /**
     * Edit an user
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

        String email;
        String password;
        final String name=null;
        String address=null;
        String phoneNumber;
        int idCity=0;
        String surname=null;
        Date birthdate=null;

        Tourist tourist;
        Message m = null;

        try{

            // activate a session to keep the user data
            HttpSession session = req.getSession();

            // insert in the session the email
            email = session.getAttribute("email").toString();

            password = req.getParameter("password");
            phoneNumber = req.getParameter("phonenumber");

            tourist = new Tourist(email,
                    password,
                    name,
                    address,
                    phoneNumber,
                    idCity,
                    surname,
                    birthdate);

            // edit the user
            //new CreateUserDatabase(getDataSource().getConnection(), tourist).editUser();
            UserDAO.editUser(tourist);

            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */

            m = new Message("Tourist successfully edited.");

            // Show the list of booking
            req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);

        } catch (NumberFormatException ex) {
            m = new Message("Cannot edit the user. " +
                    "Invalid input parameters",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot edit the user: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        } catch (NamingException e) {
            //TODO: fix
            e.printStackTrace();
        }
    }
}