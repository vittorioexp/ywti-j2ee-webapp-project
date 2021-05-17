package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Company;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.EmailSender;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletContext;
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
 * Servlet class register a user
 * @author Francesco Giurisato
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class RegisterServlet extends AbstractDatabaseServlet {


    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

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
     * Check HTTP POST requests for register
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
            return;
        }
    }

    /** Method to register and create session from user credentials
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

            //check user parameters
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

            if ( password == null || password.equals("")) {
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Password not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (!rpassword.equals(password) ) {
                ErrorCode ec = ErrorCode.DIFFERENT_PASSWORDS;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Different passwords entered.");
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
            String role="";
            // checking different parameters for tourist and company

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
                role = "tourist";

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
                role = "company";
            }

            //checking if the user is correctly created
            if ( usr == null ){
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Internal error.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            //checking the presence of a session and in case invalidate it
            HttpSession session = req.getSession(false);
            if (session != null ){
                    session.invalidate();
            }

            //encoding the user email and password in the session attribute
            String auth = email + ":" +role;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")) );
            session = req.getSession(true);
            String authHeader = "Basic " + new String( encodedAuth );
            session.setAttribute( "Authorization", authHeader );

            ServletContext sc = getServletContext();
            String emailsite = sc.getInitParameter("emailSite");
            String passEmail = sc.getInitParameter("passwordEmail");
            // sending confirmation email
            EmailSender mail= new EmailSender(email , emailsite , passEmail);

            //send email and check if email is sent correctly
            if (!mail.sendConfirmationEmail("Your Way To Italy - Account successfully registered",
                    "Congratulations, your account has successfully been registered. " +
                            "You can now start your Journey!")){
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Email not sent.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            //everything went fine
            Message success = new Message("Successful registration!");
            success.toJSON(res.getOutputStream());
            res.setStatus(HttpServletResponse.SC_OK);

        }catch (Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Something went wrong during registration",
                    ec.getHTTPCode(), ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }

    }

}


