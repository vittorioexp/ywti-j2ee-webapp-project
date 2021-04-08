package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

/**
 * Servlet class to delete a booking (mark as DELETED)
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class DeleteBookingServlet extends AbstractDatabaseServlet{

    /**
     * Edit a booking
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int idAdvertisement = 0;    // TODO: This can be get somehow
        Date date = null;
        Time time = null;
        String state = null;
        int numBooking=0;

        Booking booking;

        try{
            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email")==null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            String emailTourist = session.getAttribute("email").toString();
            String password = session.getAttribute("password").toString();

            booking = new Booking(
                    emailTourist,
                    idAdvertisement,
                    date,
                    time,
                    numBooking,
                    state
            );

            // delete the booking
            BookingDAO.deleteBooking(booking);

            // TODO: numTotItem+=numBooking in the Advertisement relative to the deleted booking

            req.getRequestDispatcher("/jsp/show-bookings-of-user.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot edit the booking. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
    }
}
