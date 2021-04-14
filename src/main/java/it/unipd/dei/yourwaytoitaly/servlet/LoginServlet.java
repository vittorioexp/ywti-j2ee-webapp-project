package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Message;
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
 * @version 1.0
 * @since 1.0
 */

public class LoginServlet extends AbstractDatabaseServlet {

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
     * Manages HTTP GET and POST requests for login
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

        if (op.equals ("login")) {
            // the requested operation is register
            login(req, res);
        }
        else{
            // the requested operation is unknown
            ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
            Message m = new Message("Not Valid Request.", ec.getErrorCode()
                    ,"You have requested a non existing resource .");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }


    public void login (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // esposito.vittorio.1998@gmail.com

            if (email == null || email.equals("")) {
                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                Message m = new Message("Email not valid.",
                        ec.getErrorCode(),"Email not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            HttpSession session = req.getSession(false);
            if (session != null ){
                if ( email.equals(LoginServlet.getUserEmail(req)) ){
                    // User is already properly logged in
                    req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
                }else
                    // invalidate session
                    session.invalidate();
            }

            if (password == null || password.equals( "" )) {
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                Message m = new Message("Password not valid.",
                        ec.getErrorCode(),"Password not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            User usr = UserDAO.searchUserLogin(email, password);

            if (usr == null){
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                Message m = new Message( "User not found.",
                        ec.getErrorCode(),"Credentials are wrong");
                m.toJSON(res.getOutputStream());
                return;
            }

            String auth = email + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")) );

            String authHeader = "Basic " + new String( encodedAuth );
            session = req.getSession(true);
            session.setAttribute( "Authorization", authHeader );

            Message success = new Message("Successful login!");
            req.setAttribute("message", success);
            res.setStatus(HttpServletResponse.SC_OK);
            res.sendRedirect("/index");

        }catch (Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Failed to login.",
                    ec.getErrorCode(), ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
           return;
        }
    }

    private static String getPair( HttpServletRequest req ){
        String authorization = (String)req.getAttribute("Authorization");
        if (authorization==null) return "";
        String a = new String (Base64.getDecoder().decode(authorization.substring(6)));
        if ( a == null )
            return new String("");
        else
            return a;
    }

    private static String getUserEmail( HttpServletRequest req ){
        String[] credentials = getPair(req).split(":");
        return credentials[0];
    }


    private static String getUserPassword( HttpServletRequest req ){
        String[] credentials = getPair(req).split(":");
        return credentials[1];
    }

    public static boolean checkSessionEmail(HttpServletRequest req, String email){
        return  email.equals(getUserEmail(req));
    }


}




