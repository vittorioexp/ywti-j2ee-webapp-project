package it.unipd.dei.yourwaytoitaly.servlet;


import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



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

                    if (u == null) {
                        ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                        Message m = new Message("User not found.",
                                ec.getErrorCode(),"User not found.");
                        res.setStatus(ec.getHTTPCode());
                        req.setAttribute("message", m);
                        req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
                    }

                    if (u instanceof Tourist) {
                        req.setAttribute("tourist",(Tourist) u);
                    } else if (u instanceof Company) {
                        req.setAttribute("company",(Company) u);
                    }

                    req.getRequestDispatcher("/jsp/show-profile.jsp").forward(req, res);

                } catch (Exception ex) {
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    Message m = new Message("Cannot show the user. ",
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