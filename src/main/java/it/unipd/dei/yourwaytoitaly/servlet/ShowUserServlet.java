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

/**
 * Servlet class to show the user profile
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class ShowUserServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        try {

            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email")==null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // get email and password of the user from the session
            String emailSession = session.getAttribute("email").toString();
            String passwordSession = session.getAttribute("password").toString();

            User u = UserDAO.searchUserLogin(emailSession, passwordSession);

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

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot show the user. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}