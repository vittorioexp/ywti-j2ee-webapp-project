package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchTypeAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

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

        HttpSession session = req.getSession();

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

        int idAdvertisement;    // This is set by the DB
        String emailCompany = session.getAttribute("email").toString();
        int idType=0;           // This will be get by inspecting Type_advertisement
        int score;              // This will be calculated

        Advertisement advertisement  = null;
        Message m = null;

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


            //TODO: Here make some controls of the inserted data
            if(title=="" || title==null || title.length()<5){
                Message msg = new Message("Input value not valid.",
                        "E3","Title of the advertisement empty.");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }
            if(numTotItem<=0 || price<=0){
                Message msg = new Message("Input value not valid.",
                        "E1","Total number of item or price not valid");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }
            if(dateEnd.compareTo(dateStart)<0 || timeEnd.compareTo(timeStart)<0){
                Message msg = new Message("Input value not valid.","E2","Dates entered are not compatible.");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }
            if(description=="" || description==null || description.length()>10000){
                Message msg = new Message("Input value not valid.","E3","Description of the advertisement empty.");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }


            // Calculate the score
            score = (int) (price/3.14);
            idType = new SearchTypeAdvertisementDatabase(getDataSource().getConnection(), type_adv)
                    .searchTypeAdvertisement().getIdType();

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

            // updates the advertisement
            advertisement =
                    new CreateAdvertisementDatabase(getDataSource().getConnection(), advertisement)
                            .createAdvertisement();

            if(advertisement==null){
                Message msg = new Message("Generic error","E5","Cannot create the advertisement.");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/create-advertisement.jsp").forward(req, res);
            }
            /*
            m = new Message(String.format("Advertisement %s successfully created. ID:",
                    advertisement.getIdAdvertisement()));
               */
            m = new Message("Feedback successfully completed. IDs: "+ emailCompany +" and "+idAdvertisement);

            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

        } catch (NumberFormatException ex) {
            m = new Message("Cannot create the advertisement. " +
                    "Invalid input parameters",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot create the advertisement.: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }

    }
}