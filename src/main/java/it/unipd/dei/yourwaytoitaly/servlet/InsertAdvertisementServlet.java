package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.ImageDAO;
import it.unipd.dei.yourwaytoitaly.database.TypeAdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Image;
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
 * Servlet class to create a new advertisement
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public final class InsertAdvertisementServlet extends AbstractDatabaseServlet {

    /**
     * Create an advertisement
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
}