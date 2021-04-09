package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.CityDAO;
import it.unipd.dei.yourwaytoitaly.database.TypeAdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Servlet class to show:
 * - to a company the bookings of an advertisement
 * - to a user his bookings
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class ShowBookingListServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String op = req.getRequestURI();
        boolean check = op.contains("tourist");
        List<Booking> listBookings=null;

        try {
            //Check if tourist
           if(check) {

               // check if a session is valid
               User u = new SessionCheckServlet(req, res).getUser();
               if (u == null) {
                   ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                   Message m = new Message("User not found.",
                           ec.getErrorCode(), "User not found.");
                   res.setStatus(ec.getHTTPCode());
                   req.setAttribute("message", m);
                   req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
               }

               // insert in the session the email
               String email = u.getEmail();

               listBookings = BookingDAO.searchBookingByUser(email);

               req.setAttribute("bookings-list", listBookings);
               req.getRequestDispatcher("/jsp/show-bookings-of-user.jsp").forward(req, res);
           }else{

               //Check if company

               int idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

               listBookings = BookingDAO.searchBookingByAdvertisement(idAdvertisement);

                req.setAttribute("bookings-list", listBookings);
                req.getRequestDispatcher("/jsp/show-bookings-of-advertisement.jsp").forward(req, res);
            }
        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot show the bookings. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }


    }

}