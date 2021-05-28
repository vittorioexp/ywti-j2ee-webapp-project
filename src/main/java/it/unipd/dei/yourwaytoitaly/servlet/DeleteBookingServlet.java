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
 * Servlet class to mark a booking as DELETED
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */
public class DeleteBookingServlet extends AbstractDatabaseServlet{

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * Marks a booking as DELETED
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
    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        int idAdvertisement = 0;
        Date date = null;
        Time time = null;
        String state = null;
        int numBooking=0;
        String mex="";

        try{
            String emailTourist = LoginServlet.getUserEmail(req);

            // receive idAdvertisement from the hidden form
            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if ( advertisement == null ){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Cannot delete because advertisement doesn't exist.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            Booking booking = BookingDAO.searchBooking(emailTourist, idAdvertisement);
            if ( booking == null ){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Cannot delete because booking doesn't exist.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (booking.getState().equalsIgnoreCase("DELETED")) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message(
                        ec.getErrorMessage(),
                        ec.getHTTPCode(), "Cannot delete because booking is already deleted.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            numBooking = booking.getNumBooking();

            // if the event is already started (or ended), user is not allowed to delete the booking
            Date today = new Date(Calendar.getInstance().getTime().getTime());
            if (today.compareTo(advertisement.getDateStart())>0) {
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "The event is already started. You cannot delete this booking.");
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
                    advertisement.getTitle(),
                    advertisement.getDescription(),
                    advertisement.getScore(),
                    advertisement.getPrice(),
                    advertisement.getNumTotItem()+numBooking,
                    advertisement.getDateStart(),
                    advertisement.getDateEnd(),
                    advertisement.getTimeStart(),
                    advertisement.getTimeEnd(),
                    advertisement.getEmailCompany(),
                    advertisement.getIdType()
            );

            AdvertisementDAO.editAdvertisement(advertisement);

            res.setStatus(HttpServletResponse.SC_OK);
            Message m = new Message("Booking successfully deleted!");
            m.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot delete this booking: " + ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Marks a booking as DELETED.
     * This method can be used by an HTML form trying to perform a DELETE
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
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        doDelete(req, res);
    }
}
