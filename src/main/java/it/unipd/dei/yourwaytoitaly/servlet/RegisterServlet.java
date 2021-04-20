package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Company;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.EmailSender;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Base64;

import static java.lang.Integer.parseInt;

/**
 * Servlet class, to be written
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class RegisterServlet extends AbstractDatabaseServlet {

    /**
     * Manages HTTP GET requests for register
     *
     * @param req
     *            the request from the client.
     * @param res
     *            the response from the server.
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     * @throws IOException
     *             if any problem occurs while communicating between the client
     *             and the server.
     */


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        handleRequest(req, res);
    }

    /**
     * Manages HTTP POST requests for register
     * @param req
     *            the request from the client.
     * @param res
     *            the response from the server.
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     * @throws IOException
     *             if any problem occurs while communicating between the client
     *             and the server.
     */


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        handleRequest(req, res);
    }

    /**
     * Manages HTTP GET and POST requests for register
     * @param req
     *            the request from the client.
     * @param res
     *            the response from the server.
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     * @throws IOException
     *             if any problem occurs while communicating between the client
     *             and the server.
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";
    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("user") + 5);

        if (op.equals ("register")) {
            // the requested operation is register
            register(req, res);
        }
        else{
            // the requested operation is unknown
            ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
            Message m = new Message(ec.getErrorMessage(), ec.getHTTPCode()
                    ,"You have requested a non existing resource.");
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            m.toJSON(res.getOutputStream());
            //res.sendRedirect(req.getContextPath() + "/user/do-register");
        }
    }


    public void register (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {
            String userType = req.getParameter("userType");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String rpassword = req.getParameter("rpassword");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");
            int idCity = parseInt(req.getParameter("city"));

            if ( email == null || email.equals("") ) {
                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Email not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if ( userType == null ) {
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message(ec.getErrorMessage(), ec.getHTTPCode()
                        ,"User type not selected.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if ( password == null || password.equals("") || !rpassword.equals(password) ) {
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Password not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if ( name == null || name.equals("")  ) {
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Name not entered.");
                m.toJSON(res.getOutputStream());
                return;
            }

            if ( address == null || address.equals("") ) {
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Address not entered.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if ( phone == null || phone.equals( "" ) ){
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Phone number not entered.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            User usr;

            if ( userType.equals("tourist") ){

                String birthDate = req.getParameter("birthDate");
                Date birthDateFormatted = null;
                //birthDate : a String object representing a date in in the format "yyyy-[m]m-[d]d".
                //The leading zero for mm and dd may also be omitted.
                try {
                    birthDateFormatted = Date.valueOf(birthDate);
                }
                catch ( IllegalArgumentException e ) {
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(),"Date not valid.");
                    m.toJSON(res.getOutputStream());
                    return;
                }

                String surname = req.getParameter("surname");

                if ( surname == null || surname.equals( "" )) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(),"Surname not entered.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }
                //assert birthDateFormatted != null;

                usr = UserDAO.searchUserByEmail(email);

                if (usr!=null) {
                    // the user is already registered
                    ErrorCode ec = ErrorCode.MAIL_ALREADY_USED;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(),"The user is already registered.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

                Tourist t = new Tourist(email, password , name , address , phone , idCity , surname , birthDateFormatted);
                usr = (Tourist) UserDAO.createUser(t);

            }else {
                usr = UserDAO.searchUserByEmail(email);

                if (usr!=null) {
                    // the user is already registered
                    ErrorCode ec = ErrorCode.MAIL_ALREADY_USED;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(),"The user is already registered.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }
                Company c = new Company( email, password , address , phone , idCity , name);
                usr = (Company) UserDAO.createUser(c);
            }

            if ( usr == null ){
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Internal error.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            HttpSession session = req.getSession(false);
            if (session != null ){
                    session.invalidate();
            }

            assert usr != null; // if user for some reason is null it will raise an AssertionException
            String auth = email + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")) );
            session = req.getSession(true);
            String authHeader = "Basic " + new String( encodedAuth );
            session.setAttribute( "Authorization", authHeader );

            EmailSender mail= new EmailSender(email);

            //send email and check if email is sent correctly
            if (!mail.sendConfirmationEmail("YourWayToItaly:Account successfully registered",
                    "Congratulations, your account has successfully been registered. " +
                            "You can now start your Journey!")){
                ErrorCode ec = ErrorCode.SEND_MAIL_EROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Email not sent.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // login credentials were correct: we redirect the user to the homepage
            // now the session is active and its data can used to change the homepage
            //res.sendRedirect(req.getContextPath()+"/jsp/homepage.jsp");

            Message success = new Message("Successful registration!");
            req.setAttribute("message", success);
            res.setStatus(HttpServletResponse.SC_OK);
            res.sendRedirect(req.getContextPath() + "/index");

        }catch (Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Internal error.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            //req.setAttribute("message", m);
            //res.sendRedirect(req.getContextPath() + "/user/do-register");
        }

    }

}


