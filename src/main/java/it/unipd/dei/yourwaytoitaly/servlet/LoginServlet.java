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
            req.setAttribute("message", m);
            res.sendRedirect(req.getContextPath() + "/user/do-login");
        }
    }


    public void login (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");

            if ( email == null || email.equals( "" ) ) {
                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Input not valid",
                        ec.getErrorCode(), ec.getErrorMessage()));
                res.sendRedirect(req.getContextPath() + "/user/do-login");
            }

            HttpSession session = req.getSession();
            if (session != null ){
                if ( !email.equals(LoginServlet.getUserEmail(req)) ){
                    session.invalidate();
                }else
                    return;
            }

            if ( userType == null ) {
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message( "Input not valid.",
                        ec.getErrorCode(), "User type not selected"));
                res.sendRedirect(req.getContextPath() + "/user/do-login");
            }

            if ( password == null || password.equals( "" ) ) {
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Input not valid.",
                        ec.getErrorCode(), ec.getErrorMessage()));
                res.sendRedirect(req.getContextPath() + "/user/do-login");
            }

            User usr = UserDAO.searchUserLogin(email, password);

            if ( usr == null ){
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                Message m = new Message( "User not found.",
                        ec.getErrorCode(),"credentials are wrong");
                req.setAttribute("message", m);
                res.sendRedirect(req.getContextPath() + "/user/do-login");
            }

            assert usr != null; // if user for some reason is null it will raise an AssertionException
            String auth = email + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")) );

            String authHeader = "Basic " + new String( encodedAuth );
            session.setAttribute( "Authorization", authHeader );

            // login credentials were correct: we redirect the user to the referer page
            // now the session is active
            res.sendRedirect(req.getHeader("referer"));

        }catch (Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Failed to login.",
                    ec.getErrorCode(), ex.getStackTrace().toString());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            res.sendRedirect(req.getContextPath() + "/user/do-login");
        }

    }

    private static String getPair( HttpServletRequest req ){
        String a = new String (Base64.getDecoder().decode(((String)req.getAttribute("Authorization") ).substring(6)));
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




