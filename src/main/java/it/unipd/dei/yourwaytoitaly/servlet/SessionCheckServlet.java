package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Message;
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
 * Servlet class, to be written
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class SessionCheckServlet extends AbstractDatabaseServlet {

    private final HttpSession session;

    public SessionCheckServlet (HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
        this.session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            session.invalidate();
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
        }
    }

    public String getEmail(){ return session.getAttribute("email").toString();}
    public String getPassword() {return  session.getAttribute("password").toString();}
    public User getUser() throws SQLException, NamingException { return UserDAO.searchUserLogin(getEmail(), getPassword());    }

}