package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * Servlet class to edit the user profile of a tourist
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

        String email = null;
        String password;
        final String name=null;
        String address=null;
        String phoneNumber;
        int idCity=0;
        String surname=null;
        Date birthdate=null;

        Tourist tourist;

        try{

            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email")==null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // check if the email of the session is equal to emailCompany
            String emailSession = session.getAttribute("email").toString();
            if (!emailSession.equals(email)) {
                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                Message m = new Message("User is not authorized.",
                        ec.getErrorCode(),"User is not authorized to edit this.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            password = req.getParameter("password");

            phoneNumber = req.getParameter("phonenumber");

            if (phoneNumber==null || phoneNumber.length()<10 || phoneNumber.length()>15) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input phone number is not valid. ",
                        ec.getErrorCode(), "Input phone number is not valid. " + phoneNumber);
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            tourist = new Tourist(email,
                    password,
                    name,
                    address,
                    phoneNumber,
                    idCity,
                    surname,
                    birthdate
            );

            UserDAO.editUser(tourist);

            req.getRequestDispatcher("/jsp/show-profile.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot edit the user profile. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}