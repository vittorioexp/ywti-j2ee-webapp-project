package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Company;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class to edit a user
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class EditUserServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * Edits an user
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
    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        doPost(req, res);
    }


    /**
     * Edits an user
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
            throws IOException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        String email;
        String password;
        String phoneNumber;
        String address;
        int idCity;

        try{

            User u = UserDAO.searchUserByEmail(LoginServlet.getUserEmail(req));
            if (u==null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            email = u.getEmail();

            // at this point the user exists so it is authorized to edit his own profile

            phoneNumber = req.getParameter("phonenumber");
            password = req.getParameter("password");
            address = req.getParameter("address");
            idCity = Integer.parseInt(req.getParameter("idCity"));

            if (phoneNumber==null || phoneNumber.length()<7 || phoneNumber.length()>14) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Phone number not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            if (password==null || password.length()<8 || password.length()>150) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Password not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            if (address==null || address.length()<4 || address.length()>150) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Address not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            if (idCity<=0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "IdCity not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }


            if (u instanceof Tourist) {

                u = new Tourist(
                        email,
                        password,
                        null,
                        address,
                        phoneNumber,
                        idCity,
                        null,
                        null
                );

                UserDAO.editUserProfile(u);

            } else if (u instanceof Company) {

                u = new Company(
                        email,
                        password,
                        address,
                        phoneNumber,
                        idCity,
                        null
                );

                UserDAO.editUserProfile(u);

            }
            Message success = new Message("Profile successfully edited.");
            res.setStatus(HttpServletResponse.SC_OK);
            success.toJSON(res.getOutputStream());


        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot edit the user profile: "+ ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}
