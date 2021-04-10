package it.unipd.dei.yourwaytoitaly.servlet;


import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Company;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class to edit the user profile of a company
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class EditCompanyServlet extends AbstractDatabaseServlet {
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


        //TODO: Merge EditTouristServlet with EditCompanyServlet
        String email;
        String password;
        String phoneNumber;

        try{

            User u = new SessionCheckServlet(req,res).getUser();
            if (u==null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
            }
            email = u.getEmail();
            password = u.getPassword();
            // at this point the user is authorized to edit the profile

            phoneNumber = req.getParameter("phonenumber");

            if (phoneNumber==null || phoneNumber.length()<10 || phoneNumber.length()>15) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input phone number is not valid. ",
                        ec.getErrorCode(), "Input phone number is not valid. " + phoneNumber);
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/edit-profile.jsp").forward(req, res);
            }

            u = new Company(
                    email,
                    password,
                    null,
                    phoneNumber,
                    0,
                    null
            );

            UserDAO.editUser(u);

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