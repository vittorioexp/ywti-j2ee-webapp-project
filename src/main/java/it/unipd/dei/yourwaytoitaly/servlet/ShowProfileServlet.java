package it.unipd.dei.yourwaytoitaly.servlet;


import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Servlet class to show the user profile
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class ShowProfileServlet extends AbstractDatabaseServlet {

    /**
     * Gets the user profile
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("user") + 5);
        boolean isTourist = false;
        try {
        switch (op){
            case "profile":
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
                    List<Booking> bookingList = BookingDAO.searchBookingByUser(emailTourist);
                    req.setAttribute("bookingList", bookingList);
                    List<Advertisement> advertisementList = new ArrayList<Advertisement>();
                    for (Booking b : bookingList) {
                        Advertisement adv = AdvertisementDAO.searchAdvertisement(b.getIdAdvertisement());
                        advertisementList.add(adv);
                    }
                    // shows more info about bookings
                    req.setAttribute("advertisementList", advertisementList);
                } else if (u instanceof Company) {
                    req.setAttribute("userType",(Boolean) isTourist);
                    req.setAttribute("user",(Company) u);
                    String emailCompany = u.getEmail();
                    List<Advertisement> advertisementList = AdvertisementDAO.searchAdvertisement(emailCompany);
                    req.setAttribute("advertisementList", advertisementList);
                }
                req.getRequestDispatcher("/jsp/protected/show-profile.jsp").forward(req, res);
            break;
        default:
            ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Operation unknown.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
        }
        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show the user: "+ ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
        }
    }
}