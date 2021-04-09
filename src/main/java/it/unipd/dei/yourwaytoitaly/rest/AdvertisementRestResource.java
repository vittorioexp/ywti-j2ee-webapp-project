package it.unipd.dei.yourwaytoitaly.rest;


import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.CityDAO;
import it.unipd.dei.yourwaytoitaly.database.ImageDAO;
import it.unipd.dei.yourwaytoitaly.database.TypeAdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
    public void insertAdvertisement() throws IOException, ServletException {

        // check if a session is valid
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email")==null) {
            session.invalidate();
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
        }

        // The user must give these parameters
        String title;
        String type_adv;
        String description;
        int price;
        int numTotItem;
        Date dateStart;
        Date dateEnd;
        Time timeStart;
        Time timeEnd;
        // This must be converted into idType

        int idAdvertisement = 0;    // This is set by the DB
        String emailCompany = session.getAttribute("email").toString();
        int idType=0;           // This will be get by inspecting Type_advertisement
        int score;              // This will be calculated

        Advertisement advertisement  = null;

        try{
            // retrieves the request parameters
            title = req.getParameter("title");
            type_adv = req.getParameter("type");
            description =  req.getParameter("description");
            price = Integer.parseInt(req.getParameter("price"));
            numTotItem = Integer.parseInt(req.getParameter("numTotItem"));
            dateStart = Date.valueOf(req.getParameter("dateStart"));
            dateEnd = Date.valueOf(req.getParameter("dateEnd"));
            timeStart = Time.valueOf(req.getParameter("timeStart"));
            timeEnd = Time.valueOf(req.getParameter("timeEnd"));
            Image img = new Image(0,req.getParameter("url").toString(),"",Integer.parseInt(req.getParameter("idAdvertisement")));
            ImageDAO.createImage(img);

            if(title==null || title.length()<5 || title.length()>100){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Title of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
            if(description==null || description.length()<5 || description.length()>10000){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"description of the advertisement not valid.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
            if(price<0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Price not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
            if(numTotItem<=0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Total number of item not valid");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }
            if(dateEnd.compareTo(dateStart)<0 || timeEnd.compareTo(timeStart)<0){
                ErrorCode ec = ErrorCode.WRONG_FORMAT;
                Message m = new Message("Input value not valid.",
                        ec.getErrorCode(),"Dates entered are not valid.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            score = (int) (price/3.14);
            idType = TypeAdvertisementDAO.searchTypeAdvertisement(type_adv).getIdType();

            // creates a new advertisement from the request parameters
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
                    emailCompany,
                    idType
            );

            advertisement = AdvertisementDAO.createAdvertisement(advertisement);

            if(advertisement==null){
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message("Generic error",
                        ec.getErrorCode(),"Cannot create the advertisement.");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            req.setAttribute("idAdvertisement",idAdvertisement);
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot create the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
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

    }

    /**
     * Updates an employee in the database.
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


        try{

            // check if a session is valid
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("email") == null) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // check if the email of the session is equal to emailCompany
            String emailSession = session.getAttribute("email").toString();
            emailCompany = AdvertisementDAO.searchAdvertisement(idAdvertisement).getEmailCompany();
            if (!emailSession.equals(emailCompany)) {
                ErrorCode ec = ErrorCode.WRONG_CREDENTIALS;
                Message m = new Message("User is not authorized.",
                        ec.getErrorCode(),"User is not authorized to edit this advertisement");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            // receive idAdvertisement from the hidden form
            idAdvertisement = Integer.parseInt(req.getParameter("idAdvertisement"));

            price = Integer.parseInt(req.getParameter("price"));
            numTotItem = Integer.parseInt(req.getParameter("numTotItem"));

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

            score = (int) (price/3.14);

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

            req.setAttribute("idAdvertisement",idAdvertisement);
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

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

        switch (op){
            case "advertisement/":
                // list all the advertisements of a company

                // check if a session is valid
                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute("email")==null) {
                    session.invalidate();
                    req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
                }

                // insert in the session the email
                String email = session.getAttribute("email").toString();

                listAdvertisement = AdvertisementDAO.searchAdvertisement(email);

                req.setAttribute("advertisement-list", listAdvertisement);
                req.getRequestDispatcher("/jsp/show-advertisement-list.jsp").forward(req, res);
                break;

            default:
                // list all the advertisements requested by the user

                int idCity;
                Date date=null;
                int idType;

                date = Date.valueOf(req.getParameter("date").toString());
                City city = CityDAO.searchCity(req.getParameter("city"));
                TypeAdvertisement typeAdvertisement =
                        TypeAdvertisementDAO.searchTypeAdvertisement(req.getParameter("typeAdvertisement"));

                // TODO: check input fields
                if (date == null || city == null || typeAdvertisement == null) {
                    ErrorCode ec = ErrorCode.WRONG_FORMAT;
                    Message m = new Message("Input value not valid.",
                            ec.getErrorCode(),"Input value not valid.");
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
                }

                idCity = city.getIdCity();
                idType = typeAdvertisement.getIdType();

                listAdvertisement = AdvertisementDAO.searchAdvertisement(idCity,idType,date);

                req.setAttribute("advertisement-list", listAdvertisement);
                req.getRequestDispatcher("/jsp/show-advertisement-list.jsp").forward(req, res);
                break;
        }
    }


}
