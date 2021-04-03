package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.CreateAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.database.SearchTypeAdvertisementDatabase;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Servlet class to create a new advertisement
 *
 * @author Vittorio Esposito
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
        String emailCompany=""; // TODO: This can be get by the servlet
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
            if(numTotItem<=0 || price<=0){
                Message msg = new Message("Input value not valid.","E1","Total number of item or price not valid");
                //...
            }
            if(dateEnd.compareTo(dateStart)<0 || timeEnd.compareTo(timeStart)<0){
                Message msg = new Message("Input value not valid.","E2","Dates entered are not compatible.");
                //...
            }
            if(description=="" || description==null){
                Message msg = new Message("Input value not valid.","E3","Description of the advertisement empty.");
                //...
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

            m = new Message(String.format("Advertisement %s successfully created. ID:",
                    advertisement.getIdAdvertisement()));

            // Show the advertisement just created in a web page


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