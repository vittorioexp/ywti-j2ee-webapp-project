package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Company;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

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


        switch (op){
            case "login/":
                // the requested operation is login
                login(req, res);
                break;
            default:
                // the requested operation is unknown
                sendError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }


    public void login (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");

            if ( email == null || email == "" ) {

                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                res.setStatus(ec.getHTTPCode());

                req.setAttribute("message", new Message("Email missing"));
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            }


            if ( userType == null ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("User type not selected"));
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            }

            if ( password == null || password == "" ) {

                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                res.setStatus(ec.getHTTPCode());

                req.setAttribute("message", new Message("Password missing"));
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            }

            User usr = null;

            if ( userType == "tourist"){

                //usr = (Tourist) new SearchUserLoginDatabase( getDataSource().getConnection() , email , password).SearchUserLogin();
                usr = (Tourist) UserDAO.searchUserLogin(email,password);

            }else {

                //usr = (Company) new SearchUserLoginDatabase( getDataSource().getConnection() , email , password).SearchUserLogin();
                usr = (Company) UserDAO.searchUserLogin(email,password);
            }

            if ( usr == null ){

                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                res.setStatus(ec.getHTTPCode());
                Message m = new Message( "credentials are wrong");
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            }

            HttpSession session = req.getSession();

            session.setAttribute("email", usr.getEmail());
            session.setAttribute("role", usr.getType());

            // login credentials were correct: we redirect the user to the homepage
            // now the session is active and its data can used to change the homepage
            res.sendRedirect(req.getContextPath()+"/jsp/index.jsp");


        }catch (SQLException | NamingException e){
            // TODO: handle properly NamingException
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            Message m = new Message( "Failed to connect to database");
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
        }

    }

}




