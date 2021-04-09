package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;
import org.json.JSONObject;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;


/**
 * Servlet class to show the user profile
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class ShowUserServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("user") + 5);

        switch (op){
            case "profile/":
                try {

                    User u = new SessionCheckServlet(req, res).getUser();

                    JSONObject resJSON = new JSONObject();

                    if (u instanceof Tourist) {
                        resJSON.put("email", u.getEmail());
                        resJSON.put("name", u.getName());
                        resJSON.put("surname", ((Tourist) u).getSurname());
                        resJSON.put("birth-date", ((Tourist) u).getBirthDate());
                        resJSON.put("email", u.getEmail());
                        resJSON.put("address", u.getAddress());
                        resJSON.put("phone-number", u.getPhoneNumber());
                    } else if (u instanceof Company) {
                        resJSON.put("email", u.getEmail());
                        resJSON.put("name", u.getName());
                        resJSON.put("email", u.getEmail());
                        resJSON.put("address", u.getAddress());
                        resJSON.put("phone-number", u.getPhoneNumber());
                    }

                    res.setStatus(HttpServletResponse.SC_OK);
                    res.setContentType("application/json");
                    res.getWriter().write((new JSONObject()).put("data", resJSON).toString());

                } catch (SQLException | NamingException ex) {
                    ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                    Message m = new Message("Cannot show the user.",
                            ec.getErrorCode(), ex.getMessage());
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
                }
                break;
            default:
                ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
                Message m = new Message("Operation unknown. ",
                        ec.getErrorCode(), ec.getErrorMessage());
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
    }
}