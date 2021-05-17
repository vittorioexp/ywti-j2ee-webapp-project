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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;


/**
 * Servlet creating the session after verifying user login and password
 * @author Francesco Giurisato
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class LoginServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";


    /**
     * Manages HTTP GET requests for login
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
     * Manages HTTP POST requests for login
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
     * Check HTTP GET and POST requests for login
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

        if (op.contains("login")) {
            // the requested operation is register
            login(req, res);
        }
        else{
            // the requested operation is unknown
            ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
            Message m = new Message(ec.getErrorMessage(), ec.getHTTPCode()
                    ,"You have requested a non existing resource.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /** Method to create session from user credentials
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
    public void login (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            //checkin email

            if (email == null || email.equals("")) {
                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Email not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            //check if there's an active session
            HttpSession session = req.getSession(false);
            if (session != null ){
                if ( email.equals(LoginServlet.getUserEmail(req)) ){
                    // User is already properly logged in
                    Message m = new Message("User is already properly logged in");
                    res.setStatus(200);
                    m.toJSON(res.getOutputStream());
                    return;
                }else
                    // invalidate session
                    session.invalidate();
            }

            // checking password
            if (password == null || password.equals( "" )) {
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Password not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            //checking the presence of the user in the dB
            User usr = UserDAO.searchUserLogin(email, password);

            if (usr == null){
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                Message m = new Message( ec.getErrorMessage(),
                        ec.getHTTPCode(),"Credentials are wrong.");
                m.toJSON(res.getOutputStream());
                return;
            }

            String role = "";
            if (usr instanceof Tourist){
                role = "tourist";
            }else if (usr instanceof Company){
                role = "company";
            }

            //encoding the user email and password in the session attribute
            String auth = email + ":" + role;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")) );
            //creating the session and setting the attribute
            String authHeader = "Basic " + new String( encodedAuth );
            session = req.getSession(true);
            session.setAttribute( "Authorization", authHeader );

            res.setStatus(HttpServletResponse.SC_OK);

            Message success = new Message("Successful login!");
            success.toJSON(res.getOutputStream());


        }catch (Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Failed to login.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
           return;
        }
    }


    /** Method to get the decoded pair email:role from authorization session attribute
     * @param req HttpServletRequest to get session attribute
     * @return String containing the pair.  Empty String if session authorization attribute not present
     * */

    private static String getPair( HttpServletRequest req ){
        HttpSession session = req.getSession(false);
        if (session==null) return "";
        String authorization = (String)session.getAttribute("Authorization");
        if (authorization==null) return "";
        String a = new String (Base64.getDecoder().decode(authorization.substring(6)));
        if ( a == null ) return "";
        return a;
    }

    /** Method to get the decoded pair email:password from authorization session attribute
     * @param req HttpServletRequest to get session attribute
     * @return String containing the pair.  Empty String if session authorization attribute not present
     * */

    public static String getUserEmail( HttpServletRequest req ){
        String[] credentials = getPair(req).split(":");
        return  credentials[0];
    }

    /** Method to get the user role from session
     * @param req HttpServletRequest to get session attribute
     * @return String containing the role. Empty String if session authorization attribute not present
     * */

    public static String getUserRole( HttpServletRequest req ){
        String[] credentials = getPair(req).split(":");
        return credentials[1];
    }


    /** Method compairing a given email with the session one
     * @param req HttpServletRequest to get session attribute
     * @param email email to compare
     * @return true if the emails coincide, false if not.
     * */

    public static boolean checkSessionEmail(HttpServletRequest req, String email){
        return  email.equals(getUserEmail(req));
    }


}




