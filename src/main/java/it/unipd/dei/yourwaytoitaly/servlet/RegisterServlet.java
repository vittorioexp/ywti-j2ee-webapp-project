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
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

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

                Message m = new Message("Input value not valid.",
                        "E4","Email not inserted or not valid.");
                ErrorCode ec = ErrorCode.EMAIL_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }


            if ( userType == null ) {

                Message m = new Message("Input value not valid.",
                        "E3","User type not selected.");
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            if ( password == null || password.equals("")  ) {

                Message m = new Message("Input value not valid.",
                        "E5","Password not valid.");
                ErrorCode ec = ErrorCode.PASSWORD_MISSING;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            if ( name == null || name.equals("")  ) {

                Message m = new Message("Input value not valid.",
                        "E3","Name not present.");
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            if ( address == null || address.equals("") ) {

                Message m = new Message("Input value not valid.",
                        "E3","Address not present.");
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            if ( phone == null || phone.equals( "" ) ){

                Message m = new Message("Input value not valid.",
                        "E3","Phone number not present.");
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            User usr = null;

            if ( userType.equals("tourist") ){

                String birthDate = req.getParameter("birthDate");
                Date birthDateFormatted = null;
                //birthDate : a String object representing a date in in the format "yyyy-[m]m-[d]d".
                //The leading zero for mm and dd may also be omitted.
                try {
                    birthDateFormatted = Date.valueOf(birthDate);
                }
                catch ( IllegalArgumentException e ) {

                    Message m = new Message("Input value not valid.",
                            "E3","Date not valid.");
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

                }

                String surname = req.getParameter("surname");

                if ( surname == null || surname.equals( "" )) {

                    Message m = new Message("Input value not valid.",
                            "E3","Surname not present.");
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

                }

                Tourist t = new Tourist(email, password , name , address , phone , idCity , surname , birthDateFormatted);
                //usr = (Tourist) new CreateUserDatabase( getDataSource().getConnection() , t).createUser();
                usr = (Tourist) UserDAO.createUser(t);
            }else {

                Company c = new Company( email, password , address , phone , idCity , name);
                //usr = (Company) new CreateUserDatabase( getDataSource().getConnection() , c).createUser();
                usr = (Company) UserDAO.createUser(c);
            }

            if ( usr == null ){

                Message m = new Message("Failed creating user account.",
                        "E3","Something went wrong creating user account.");
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

            }

            HttpSession session = req.getSession();

            assert usr != null; // if user for some reason is null it will raise an AssertionException
            session.setAttribute("email", usr.getEmail());
            session.setAttribute("role", usr.getType());

            // login credentials were correct: we redirect the user to the homepage
            // now the session is active and its data can used to change the homepage
            res.sendRedirect(req.getContextPath()+"/jsp/index.jsp");


        }catch (SQLException e){


            Message m = new Message("Failed to connect to database.",
                    "E3","Something went wrong creating user account in the database.");
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

        }catch (NamingException e){

            Message m = new Message("Wrong Format.",
                    "E1","The data format is not valid.");
            ErrorCode ec = ErrorCode.WRONG_FORMAT;
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);

        }

    }

}


