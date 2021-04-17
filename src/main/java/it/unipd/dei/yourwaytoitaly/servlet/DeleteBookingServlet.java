package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

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

        int idAdvertisement = 0;
        Date date = null;
        Time time = null;
        String state = null;
        int numBooking=0;
        String mex="";

        try{
            // TODO: when deleting a booking, remove the user from a score
            String emailTourist = LoginServlet.getUserEmail(req);

            // receive idAdvertisement from the hidden form
            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if ( advertisement == null ){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message("Cannot delete.",
                        ec.getErrorCode(), "Cannot delete because advertisement doesn't exist!");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);
            if ( booking == null ){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message("Cannot delete.",
                        ec.getErrorCode(), "Cannot delete because booking doesn't exist!");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (booking.getState().equalsIgnoreCase("DELETED")) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message("Cannot delete a booking.",
                        ec.getErrorCode(), "Cannot delete because booking is already deleted!");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            numBooking = booking.getNumBooking();

            // if the event is already started (or ended), user is not allowed to delete the booking
            Date today = new Date(Calendar.getInstance().getTime().getTime());
            if (today.compareTo(advertisement.getDateStart())>0) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message("Cannot delete the booking. ",
                        ec.getErrorCode(), "The event is already started!");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

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
            // numTotItem+=numBooking in the Advertisement relative to the deleted booking


            advertisement = new Advertisement(
                    idAdvertisement,
                    null,
                    null,
                    advertisement.getScore(),
                    advertisement.getPrice(),
                    advertisement.getNumTotItem()+numBooking,
                    null,
                    null,
                    null,
                    null,
                    null,
                    0
            );

            AdvertisementDAO.editAdvertisement(advertisement);

            res.setStatus(HttpServletResponse.SC_OK);
            res.sendRedirect(req.getContextPath()+"/user/profile");

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot delete the booking. " + mex,
                    ec.getErrorCode(), ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}
