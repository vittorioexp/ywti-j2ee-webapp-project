package it.unipd.dei.yourwaytoitaly.servlet;


import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Servlet class to show the user profile
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class ShowProfileServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("user") + 5);
        boolean isTourist = false;

        switch (op){
            case "profile":
                try {

                    User u = UserDAO.searchUserByEmail(LoginServlet.getUserEmail(req));

                    if (u == null) {
                        ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                        Message m = new Message(ec.getErrorMessage(),
                                ec.getHTTPCode(),"User not found.");
                        res.setStatus(ec.getHTTPCode());
                        m.toJSON(res.getOutputStream());
                    }

                    if (u instanceof Tourist) {
                        isTourist = true;
                        req.setAttribute("userType",(Boolean) isTourist);
                        req.setAttribute("user",(Tourist) u);
                        String emailTourist = u.getEmail();
                        req.setAttribute("score", UserDAO.getUserScore(emailTourist));
                        List<Booking> listBookings = BookingDAO.searchBookingByUser(emailTourist);
                        req.setAttribute("bookingList", listBookings);
                    } else if (u instanceof Company) {
                        req.setAttribute("userType",(Boolean) isTourist);
                        req.setAttribute("user",(Company) u);
                        String emailCompany = u.getEmail();
                        List<Advertisement> listAdvertisement = AdvertisementDAO.searchAdvertisement(emailCompany);
                        req.setAttribute("advertisementList", listAdvertisement);
                    }
                    req.getRequestDispatcher("/jsp/protected/show-profile.jsp").forward(req, res);
//                    res.sendRedirect(req.getContextPath() + "/jsp/protected/show-profile.jsp");

                } catch (Exception ex) {
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(), "Cannot show the user.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                }
                break;
            default:
                ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Operation unknown.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
            }
    }
}