package it.unipd.dei.yourwaytoitaly.rest;


import it.unipd.dei.yourwaytoitaly.database.*;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.servlet.LoginServlet;
import it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public class AdvertisementRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Employee} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public AdvertisementRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    /**
     * Creates an Advertisement
     *
     * @throws IOException
     *             if any error occurs
     * @throws ServletException
     *             if any error occurs
     */
    public void insertAdvertisement() throws ServletException, IOException {

        try{
            // check if a session is valid
            String email = LoginServlet.getUserEmail(req);
            if (email.equals("")) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

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

            // check if the servlet needs to receive images or if a json object (advertisement)
            if (!ServletFileUpload.isMultipartContent(req)) {

                // receive JSON object
                Advertisement advertisement = Advertisement.fromJSON(req.getInputStream());

                // control the parameters
                title = advertisement.getTitle();
                if(title==null || title.length()<5 || title.length()>100){
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Title of the advertisement not valid.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }
                description = advertisement.getDescription();
                if(description==null || description.length()<5 || description.length()>10000){
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"description of the advertisement not valid.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }
                price = advertisement.getPrice();
                if(price<0){
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Price not valid");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

                numTotItem = advertisement.getNumTotItem();
                if(numTotItem<=0){
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Total number of item not valid");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

                dateStart = advertisement.getDateStart();
                dateEnd = advertisement.getDateEnd();
                timeStart = advertisement.getTimeStart();
                timeEnd = advertisement.getTimeEnd();

                if (dateStart==null) dateStart=Date.valueOf("08:30:00");
                if (dateEnd==null) dateEnd=Date.valueOf("18:00:00");

                if(dateEnd.compareTo(dateStart)<0) {
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Dates entered are not valid.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

                if (dateStart.compareTo(dateEnd)==0 && timeEnd.compareTo(timeStart)<0) {
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Times entered are not valid.");
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
                    Message m = new Message("Generic error",
                            ec.getErrorCode(),"Cannot create the advertisement.");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

                // set attribute
                req.setAttribute("idAdvertisement",String.valueOf(idAdvertisement));

                Message success = new Message("Successful creation!");
                req.setAttribute("message", success);
                res.setStatus(HttpServletResponse.SC_OK);
                res.sendRedirect(req.getContextPath() + "/upload-images/" + String.valueOf(idAdvertisement));

            } else {
                String pathName="";
                String URI = req.getRequestURI();
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                int count = 1;
                for (FileItem item : multiparts) {
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        switch (name) {
                            case "idAdvertisement":
                                // TODO: check if idAdv > 0
                                // TODO: check if the company is authorized to upload images about this advertisement
                                idAdvertisement = Integer.parseInt(value);
                                break;
                            case "description":
                                description = value;
                                break;
                            default:
                                break;
                        }
                    } else {
                        // TODO: check file extension
                        // TODO: fix file path
                        // Save the picture inside the disk
                        pathName = System.getProperty("user.dir");
                        pathName = pathName.substring(0, pathName.lastIndexOf("bin"));
                        pathName += "webapps/ywti_wa2021_war/res/img/" +  String.valueOf(idAdvertisement) + "/";
                        pathName += String.valueOf(count) + ".png";
                        try { item.write(new File(pathName)); }
                        catch (Exception e) {}


                        // Save the image URI inside the DB
                        pathName = URI.substring(0, URI.lastIndexOf("advertisement-create"));
                        pathName += "res/img/" + String.valueOf(idAdvertisement) + "/" + String.valueOf(count) + ".png";
                        Image img = new Image
                                (
                                        0,
                                        pathName,
                                        description,
                                        idAdvertisement
                                );
                        ImageDAO.createImage(img);
                        count++;
                    }

                }
                Message success = new Message(pathName);
                req.setAttribute("message", success);
                res.setStatus(HttpServletResponse.SC_OK);
                res.sendRedirect(req.getContextPath() + "/user/profile");
            }


        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("BLACannot create the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.setAttribute("idAdvertisement", 0);
            m.toJSON(res.getOutputStream());
            return;
        }
    }

    /**
     * Reads an Advertisement from the database.
     *
     * @throws IOException
     *             if any error occurs
     * @throws ServletException
     *             if any error occurs
     */
    public void showAdvertisement() throws IOException, ServletException {

        try {
            // TODO: tell the JSP if the user is a tourist or a company to show properly booking/feedback forms
            String idAdvertisement = req.getRequestURI();
            idAdvertisement = idAdvertisement.substring(idAdvertisement.lastIndexOf("advertisement") + 14);
            Advertisement advertisement = AdvertisementDAO.searchAdvertisement(Integer.parseInt(idAdvertisement));

            //advertisement.toJSON(res.getOutputStream());
            req.setAttribute("advertisement", advertisement.toJSON());

            List<Image> imageList = ImageDAO.searchImageByIdAdvertisement(Integer.parseInt(idAdvertisement));

            if (imageList.size()!=0) {
                List<String> filepathList = new ArrayList<String>();
                for (Image image : imageList) {
                    String filepath = image.getPath();
                    filepathList.add(filepath);
                }
                req.setAttribute("filepath-list", filepathList);
            } else {
                req.setAttribute("filepath-list", new ArrayList<String>());
            }

            // The owner can see the booking list relative to this advertisement: check if a session is valid
            if (LoginServlet.checkSessionEmail(req, advertisement.getEmailCompany())) {
                List<Booking> listBookings = BookingDAO.searchBookingByAdvertisement(Integer.parseInt(idAdvertisement));
                req.setAttribute("booking-list", listBookings);
            } else {
                req.setAttribute("booking-list", new ArrayList<Booking>());
            }

            List<Feedback> feedbackList = FeedbackDAO.searchFeedbackByAdvertisement(Integer.parseInt(idAdvertisement));
            double rate = 0;
            if (feedbackList.size()!=0) {
                for (Feedback f: feedbackList) {
                    rate += f.getRate();
                }
                rate/=feedbackList.size();
                req.setAttribute("feedback-list", feedbackList);
                req.setAttribute("rate", (int) rate);

            } else {
                req.setAttribute("feedback-list", new ArrayList<Feedback>());
                req.setAttribute("rate", 0);
            }

            Message success = new Message("Successful show of the advertisement!");
            req.setAttribute("message", success);
            //res.setStatus(HttpServletResponse.SC_OK);
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot show the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
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
        String emailCompany = null;
        String emailCompanyAdvertisement = null;
        Advertisement advertisement;
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("advertisement") + 14);


        //TODO: Check if the idAdvertisement retrieved from the URI is correct

        if(op==null || Integer.parseInt(op) < 0){
            ErrorCode ec = ErrorCode.ADVERTISEMENT_NOT_FOUND;
            Message m = new Message("Advertisement not found.",
                    ec.getErrorCode(),"Advertisement not found.");
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/user/profile").forward(req, res);
        }
        idAdvertisement = Integer.parseInt(op);

        try{


            // check if the email of the session is equal to emailCompany

            User u = UserDAO.searchUserByEmail(LoginServlet.getUserEmail(req));

            if (u == null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }
            emailCompany = u.getEmail();
            emailCompanyAdvertisement = AdvertisementDAO.searchAdvertisement(idAdvertisement).getEmailCompany();

            if (!emailCompany.equals(emailCompanyAdvertisement)) {
                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                Message m = new Message("User is not authorized.",
                        ec.getErrorCode(),"User is not authorized to edit this advertisement");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/user/do-login/").forward(req, res);
            }

            AdvertisementDAO.deleteAdvertisement(idAdvertisement);

            res.sendRedirect(req.getContextPath() + "/user/profile");

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot edit the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
        }
    }
    /**
     * Edit an advertisement in the database.
     *
     * @throws IOException
     *             if any error occurs
     * @throws ServletException
     *             if any error occurs
     */
    public void editAdvertisement() throws IOException, ServletException {

        int score;
        int price;
        int numTotItem = 0;
        int idAdvertisement = 0;
        String emailCompany = null;
        Advertisement advertisement;
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("advertisement") + 14);

        try{
            // check if a session is valid
            User u = new SessionCheckServlet(req, res).getUser();
            if (u == null) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/user/do-login/").forward(req, res);
            }

            // check if the email of the session is equal to emailCompany
            String emailSession = u.getEmail();
            emailCompany = AdvertisementDAO.searchAdvertisement(idAdvertisement).getEmailCompany();
            if (!emailSession.equals(emailCompany)) {
                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                Message m = new Message("User is not authorized.",
                        ec.getErrorCode(),"User is not authorized to edit this advertisement");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/user/do-login/").forward(req, res);
            }

            // receive idAdvertisement from the hidden form
            idAdvertisement = Integer.parseInt(op);

            advertisement = Advertisement.fromJSON(req.getInputStream());

            price = advertisement.getPrice();
            numTotItem = advertisement.getNumTotItem();

            //price = Integer.parseInt(req.getParameter("price"));
            //numTotItem = Integer.parseInt(req.getParameter("numTotItem"));

            if(price<0 || price>50000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Price not valid.",
                        ec.getErrorCode(),"Price not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
            }

            if(numTotItem<0 || numTotItem>1000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Number of items not valid.",
                        ec.getErrorCode(),"Number of items not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
            }

            score = (int) Math.floor(price/3.14);

            advertisement = new Advertisement(
                    idAdvertisement,
                    null,
                    null,
                    score,
                    price,
                    numTotItem,
                    null,
                    null,
                    null,
                    null,
                    null,
                    0
            );

            AdvertisementDAO.editAdvertisement(advertisement);

            req.getRequestDispatcher("/user/profile").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot edit the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
        }
    }

    /**
     * Shows:
     * - the list of Advertisements to a tourist (based on search criteria)
     * - the list of Advertisements to a company (based on emailCompany)
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listAdvertisement() throws IOException, ServletException, SQLException, NamingException {
        //per la company: list/advertisement
        //per il tourist: homepage
        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("list") + 5);
        List<Advertisement> listAdvertisement;

        try {

            switch (op) {
                case "advertisement":
                    // list all the advertisements requested by the user

                    int idCity;
                    Date date = null;
                    int idType;

                    String d = req.getParameter("date").toString();
                    String c = req.getParameter("city");
                    String t = req.getParameter("typeAdvertisement");

                    if (d == null || c == null || t == null) {
                        ErrorCode ec = ErrorCode.WRONG_FORMAT;
                        Message m = new Message("Input value not valid.",
                                ec.getErrorCode(), "Input value not valid.");
                        res.setStatus(ec.getHTTPCode());
                        m.toJSON(res.getOutputStream());
                        return;
                    }

                    idCity = Integer.parseInt(c);
                    idType = Integer.parseInt(t);
                    date = Date.valueOf(d);

                    listAdvertisement = AdvertisementDAO.searchAdvertisement(idCity, idType, date);

                    if (listAdvertisement.size()==0) {
                        ErrorCode ec = ErrorCode.EMPTY_LIST;
                        Message m = new Message("Empty list of advertisements",
                                ec.getErrorCode(), "");
                        res.setStatus(ec.getHTTPCode());
                        m.toJSON(res.getOutputStream());
                        return;
                    }

                    new ResourceList(listAdvertisement).toJSON(res.getOutputStream());

                    break;
                default:
                    ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                    Message m = new Message("Cannot show the advertisement. Method not allowed.",
                            ec.getErrorCode(), "Method not allowed");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
            }
        } catch (SQLException ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("SQL Cannot show the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            m.toJSON(res.getOutputStream());
            return;
        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot show the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            m.toJSON(res.getOutputStream());
            return;
        }
    }
}
