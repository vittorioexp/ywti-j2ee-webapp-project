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
import java.sql.Date;
import java.sql.SQLException;

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


        if (op.equals ("register/")) {
            // the requested operation is register
            register(req, res);
        }
        else{
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

            if ( email == null || email.equals("") ) {

                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Email not inserted or not valid.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }


            if ( userType == null ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message("Input value not valid.", ec.getErrorCode()
                        ,"User type not selected.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( password == null || password.equals("")  ) {

                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Password not valid.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( name == null || name.equals("")  ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Name not present.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( address == null || address.equals("") ) {

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Address not present.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            if ( phone == null || phone.equals( "" ) ){

                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Phone number not present.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

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
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Date not valid.");
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

                }

                String surname = req.getParameter("surname");

                if ( surname == null || surname.equals( "" )) {
                    
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Surname not present.");
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

                }
                assert birthDateFormatted != null;
                Tourist t = new Tourist(email, password , name , address , phone , idCity , surname , birthDateFormatted);
                //usr = (Tourist) new CreateUserDatabase( getDataSource().getConnection() , t).createUser();
                usr = (Tourist) UserDAO.createUser(t);
            }else {

                Company c = new Company( email, password , address , phone , idCity , name);
                //usr = (Company) new CreateUserDatabase( getDataSource().getConnection() , c).createUser();
                usr = (Company) UserDAO.createUser(c);
            }

            if ( usr == null ){
                
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message("Failed creating user account.",
                        ec.getErrorCode(),"Something went wrong creating user account.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

            }

            HttpSession session = req.getSession();

            assert usr != null; // if user for some reason is null it will raise an AssertionException
            session.setAttribute("email", usr.getEmail());
            session.setAttribute("role", usr.getType());

            // login credentials were correct: we redirect the user to the homepage
            // now the session is active and its data can used to change the homepage
            res.sendRedirect(req.getContextPath()+"/jsp/homepage.jsp");


        }catch (SQLException e){

            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Failed to connect to database.",
                    ec.getErrorCode(),"Something went wrong creating user account in the database.");
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

        }catch (NamingException e){

            ErrorCode ec = ErrorCode.WRONG_FORMAT;
            Message m = new Message("Wrong Format.",
                    ec.getErrorCode(),"The data format is not valid.");
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);

        }

    }

}


