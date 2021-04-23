package it.unipd.dei.yourwaytoitaly.rest;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.BookingDAO;
import it.unipd.dei.yourwaytoitaly.database.FeedbackDAO;
import it.unipd.dei.yourwaytoitaly.database.ImageDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.servlet.LoginServlet;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


/**
 * Manages the REST API for the {@link Advertisement} resource.
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Francesco Giurisato
 * @version 1.00
 * @since 1.00
 */
public class AdvertisementRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Advertisement} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public AdvertisementRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    /**
     * Get an Advertisement
     *
     * @throws IOException
     *             if any error occurs
     */
    public void getAdvertisement() throws IOException {

        try {

            String URI = req.getRequestURI();
            // The URI should be /adv/ID

            int idAdvertisement = Integer.parseInt(URI.substring(URI.lastIndexOf("adv") + 4));


            if(idAdvertisement<=0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Cannot show the advertisement.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
            }
            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);
            if(advertisement==null){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Advertisement not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
            }


            // This should be done instead
            res.setStatus(HttpServletResponse.SC_OK);
            advertisement.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show the advertisement.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }

    }

    /**
     * Edit an Advertisement in the database.
     *
     * @throws IOException
     *             if any error occurs
     * @throws ServletException
     *             if any error occurs
     */
    public void editAdvertisement() throws IOException, ServletException, SQLException, NamingException {

        try{

            String URI = req.getRequestURI();
            // The URI should be /adv/ID

            //get idAvertisement from request URI
            int idAdvertisement = Integer.parseInt(URI.substring(URI.lastIndexOf("adv") + 4));
            Advertisement advertisement=null;

            try {
                //How to retrieve a JSON object
                advertisement = Advertisement.fromJSON(req.getInputStream());
            } catch(Exception ex) {
                ErrorCode ec = ErrorCode.BADLY_FORMATTED_JSON;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Wrong JSON object:" + ex.toString());
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            //I get the old advertisement to get the info that are not changed.
            Advertisement old_advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);

            // Checking the user is the owner of the advertisement
            String emailSession = LoginServlet.getUserEmail(req);
            String emailCompany = AdvertisementDAO.searchAdvertisement(idAdvertisement).getEmailCompany();

            if (!emailSession.equals(emailCompany)) {
                ErrorCode ec = ErrorCode.USER_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User is not authorized to edit this advertisement");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            //Checking if the attributes are changed and checking the integrity of the new values
            String title = advertisement.getTitle();
            if(title==null){
                title = old_advertisement.getTitle();
            }
            if(title.length()<5 || title.length()>100){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Title of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            String description = advertisement.getDescription();
            if(description==null){
                description = old_advertisement.getDescription();
            }
            if(description.length()<5 || description.length()>10000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Description of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            Date dateStart = advertisement.getDateStart();
            if(dateStart == null ) {
                dateStart = old_advertisement.getDateStart();
            }
            Date dateEnd = advertisement.getDateEnd();
            if(dateEnd == null ) {
                dateEnd = old_advertisement.getDateEnd();
            }
            Time timeStart = advertisement.getTimeStart();
            if(timeStart == null ) {
                timeStart = old_advertisement.getTimeStart();
            }
            Time timeEnd = advertisement.getTimeEnd();
            if(timeEnd == null ) {
                timeEnd = old_advertisement.getTimeEnd();
            }

            if (dateStart.compareTo(dateEnd)==0 && timeEnd.compareTo(timeStart)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Times not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            if(dateEnd.compareTo(dateStart)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Dates not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }


            int price = advertisement.getPrice();
            if(price == -1) {
                price = old_advertisement.getPrice();
            }
            if(price<0 || price>50000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Price not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            int numTotItem = advertisement.getNumTotItem();
            if(numTotItem == -1 ) {
                numTotItem = old_advertisement.getNumTotItem();
            }
            if(numTotItem<0 || numTotItem>1000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Number of items not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            //recalculate the new score with the new price
            int score = (int) Math.floor(price/3.14);

            //object with the new values if changed and the old ones
            advertisement = new Advertisement(
                    idAdvertisement,
                    title,
                    description,
                    score,
                    price,
                    numTotItem,
                    dateStart,
                    dateEnd,
                    timeStart,
                    timeEnd,
                    null,
                    0
            );

            advertisement = AdvertisementDAO.editAdvertisement(advertisement);

            res.setStatus(HttpServletResponse.SC_OK);
            advertisement.toJSON(res.getOutputStream());

            // for DEBUG if you want to receive the updated advertisement details
            //advertisement.toJSON(res.getOutputStream());
            //res.sendRedirect(req.getContextPath() + "/adv-show/" + String.valueOf(idAdvertisement));

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot edit the advertisement:" + ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Creates an Advertisement
     *
     * @throws IOException
     *             if any error occurs
     */
    public void insertAdvertisement() throws IOException {

        try{

            // check if a session is valid
            String email = LoginServlet.getUserEmail(req);

            int idAdvertisement = 0;
            String title = "";
            String description = "";
            int price=0;
            int numTotItem=0;
            Date dateStart=null, dateEnd=null;
            Time timeStart=null, timeEnd=null;
            int score;
            String type;
            int idType;

            Advertisement advertisement=null;
            try{
                //How to retrieve a JSON object
                advertisement = Advertisement.fromJSON(req.getInputStream());
            }catch(Exception e){
                ErrorCode ec = ErrorCode.BADLY_FORMATTED_JSON;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Wrong JSON object.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // control the parameters
            title = advertisement.getTitle();
            if(title==null || title.length()<5 || title.length()>100){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Title of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            description = advertisement.getDescription();
            if(description==null || description.length()<5 || description.length()>10000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Description of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            price = advertisement.getPrice();
            if(price<0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Price not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            numTotItem = advertisement.getNumTotItem();
            if(numTotItem<=0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Total number of item not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            dateStart = advertisement.getDateStart();
            dateEnd = advertisement.getDateEnd();
            timeStart = advertisement.getTimeStart();
            timeEnd = advertisement.getTimeEnd();


            if (timeStart==null) timeStart=Time.valueOf("05:00:00");
            if (timeEnd==null) timeEnd=Time.valueOf("23:30:00");

            if(dateEnd.compareTo(dateStart)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Dates are not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            if (dateStart.compareTo(dateEnd)==0 && timeEnd.compareTo(timeStart)<0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Times are not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            idType = advertisement.getIdType();
            score = (int) Math.floor(price/3.14);

            advertisement = new Advertisement(
                    0,
                    title,
                    description,
                    score,
                    price,
                    numTotItem,
                    dateStart,
                    dateEnd,
                    timeStart,
                    timeEnd,
                    email,
                    idType
            );

            // insert JSON inside DB
            advertisement = AdvertisementDAO.createAdvertisement(advertisement);

            if(advertisement==null){
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Cannot create the advertisement.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            res.setStatus(HttpServletResponse.SC_OK);
            advertisement.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot create the advertisement.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Updates an advertisement in the database.
     *
     * @throws IOException
     *             if any error occurs
     * @throws ServletException
     *             if any error occurs
     */
    public void deleteAdvertisement() throws IOException, ServletException {

        int idAdvertisement = 0;
        String emailCompany = "";
        String emailAdv = "";
        Advertisement advertisement;
        String URI = req.getRequestURI();

        idAdvertisement = Integer.parseInt(URI.substring(URI.lastIndexOf("adv")+4));

        if(idAdvertisement <= 0){
            ErrorCode ec = ErrorCode.AD_NOT_FOUND;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(),"Advertisement not found.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }

        try{
            // check if the email of the session is equal to emailCompany
            emailCompany = LoginServlet.getUserEmail(req);

            Advertisement a = AdvertisementDAO.searchAdvertisement(idAdvertisement);

            if (a == null){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Advertisement not existing or already deleted.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            emailAdv = a.getEmailCompany();
            if (!emailCompany.equals(emailAdv)) {
                ErrorCode ec = ErrorCode.USER_NOT_ALLOWED;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User is not authorized to delete this advertisement.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }
            AdvertisementDAO.deleteAdvertisement(idAdvertisement);

            res.setStatus(HttpServletResponse.SC_OK);
            Message m = new Message("Advertisement successfully deleted!");
            m.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot delete the advertisement.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Returns the list of Images of an Advertisement
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listImages() throws  IOException {

        try {

            String URI = req.getRequestURI();
            // The URI should be .../adv/ID/image

            // get idAdvertisement from URI
            String op = URI.substring(URI.lastIndexOf("adv") + 4, URI.lastIndexOf("/image"));
            int idAdvertisement = Integer.parseInt(op);

            if(idAdvertisement <= 0){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Advertisement not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            List<Image> imageList = ImageDAO.searchImageByIdAdvertisement(idAdvertisement);

            // This should be done instead
            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(imageList).toJSON(res.getOutputStream());


        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show list of images.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Returns the list of Feedback of an Advertisement
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listFeedback() throws IOException {

        try {
            String URI = req.getRequestURI();
            // The URI should be .../adv/ID/feedback

            // get idAdvertisement from URI
            String op = URI.substring(URI.lastIndexOf("adv") + 4, URI.lastIndexOf("/feedback"));
            int idAdvertisement = Integer.parseInt(op);

            if(idAdvertisement <= 0){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Advertisement not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Return the list of feedback
            List<Feedback> feedbackList = FeedbackDAO.searchFeedbackByAdvertisement(idAdvertisement);

            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(feedbackList).toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show list of feedback.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Returns the list of Bookings of an Advertisement
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listBookings() throws IOException {

        try {
            String URI = req.getRequestURI();
            // The URI should be .../adv/ID/booking

            // get idAdvertisement from URI
            String op = URI.substring(URI.lastIndexOf("adv") + 4, URI.lastIndexOf("/booking"));
            int idAdvertisement = Integer.parseInt(op);

            if(idAdvertisement <= 0){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Advertisement not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement);

            // The owner can see the booking list relative to this advertisement: check if a session is valid
            List<Booking> bookingList = new ArrayList<Booking>();

            //bookingList = BookingDAO.searchBookingByAdvertisement(idAdvertisement);

            if (LoginServlet.checkSessionEmail(req, advertisement.getEmailCompany())) {
                bookingList = BookingDAO.searchBookingByAdvertisement(idAdvertisement);
            }

            // This should be done instead
            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(bookingList).toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show the list of bookings.");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }

    }

    /**
     * Gets the Rate of an Advertisement
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void getRate() throws IOException {

        try {
            String URI = req.getRequestURI();
            // The URI should be .../adv/ID/feedback

            // get idAdvertisement from URI
            String op = URI.substring(URI.lastIndexOf("adv") + 4, URI.lastIndexOf("/rate"));
            int idAdvertisement = Integer.parseInt(op);

            if(idAdvertisement <= 0){
                ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Advertisement not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            // Return the list of feedback for the advertisement with idAdvertisement
            List<Feedback> feedbackList = FeedbackDAO.searchFeedbackByAdvertisement(idAdvertisement);
            double rate = 0;
            if (feedbackList.size()!=0) {
                for (Feedback f: feedbackList) {
                    rate += f.getRate();
                }
                rate/=feedbackList.size();
            }

            Rate r = new Rate((int) rate);


            // This should be done instead
            res.setStatus(HttpServletResponse.SC_OK);
            r.toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show the advertisement. ");
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Returns the list of Advertisements to a tourist (based on search criteria)
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listAdvertisements() throws IOException {

        List<Advertisement> listAdvertisement = new ArrayList<Advertisement>();

        try {
            // list all the advertisements requested by the user

            SearchParameters s = SearchParameters.fromJSON(req.getInputStream());

            int idCity = s.getIdCity();
            int idType = s.getIdType();
            Date date = s.getDateStart();

            if (date == null || idCity <= 0 || idType <= 0) {
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "Input value not valid.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            listAdvertisement = AdvertisementDAO.searchAdvertisement(idCity, idType, date);

            if(listAdvertisement.isEmpty()){
                ErrorCode ec = ErrorCode.EMPTY_LIST;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(), "No results that match with the input parameters.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(listAdvertisement).toJSON(res.getOutputStream());

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot show the advertisement:" + ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}
