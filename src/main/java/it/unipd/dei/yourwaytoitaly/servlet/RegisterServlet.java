package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateUserDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchUserLoginDatabase;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

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
                register(req, res);
                break;
            default:
                // the requested operation is unknown
                sendError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }


    public void register (HttpServletRequest req, HttpServletResponse res)  throws IOException ,ServletException {

        try {

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");
            int idCity = parseInt(req.getParameter("idCity"));



            if ( email == null || email == "" ) {

                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Email missing"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }


            if ( userType == null ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("User type not selected"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( password == null || password == "" ) {

                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Password missing"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( name == null || name == "" ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Name missing"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( address == null || address == "" ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Address missing"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( phone == null || phone == "" ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Phone missing"));
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            User usr = null;

            if ( Objects.equals(userType, "tourist") ){

                String birthDate = req.getParameter("birthDate");
                Date birthDateFormatted;
                //birthDate : a String object representing a date in in the format "yyyy-[m]m-[d]d".
                //The leading zero for mm and dd may also be omitted.
                try {
                    birthDateFormatted = Date.valueOf(birthDate);
                }
                catch ( IllegalArgumentException e ) {

                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", new Message("Birth date missing or incorrect"));
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

                }

                String surname = req.getParameter("surname");

                if ( surname == null || surname == "" ) {

                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", new Message("Surname missing"));
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

                }

                Tourist t = new Tourist(email, password , name , address , phone , idCity , surname , birthDateFormatted);
                usr = (Tourist) new CreateUserDatabase( getDataSource().getConnection() , t).createUser();

            }else {

                Company c = new Company( email, password , address , phone , idCity , name);
                usr = (Company) new CreateUserDatabase( getDataSource().getConnection() , c).createUser();

            }

            if ( usr == null ){

                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                res.setStatus(ec.getHTTPCode());
                Message m = new Message( "credentials are wrong");
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            HttpSession session = req.getSession();

            session.setAttribute("email", usr.getEmail());
            session.setAttribute("role", usr.getType());

            // login credentials were correct: we redirect the user to the homepage
            // now the session is active and its data can used to change the homepage
            res.sendRedirect(req.getContextPath()+"/jsp/index.jsp");


        }catch ( SQLException e){

            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            Message m = new Message( "Failed to connect to database");
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
        }

    }

    }

}