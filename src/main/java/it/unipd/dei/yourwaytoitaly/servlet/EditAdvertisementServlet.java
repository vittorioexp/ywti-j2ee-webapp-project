package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Message;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Servlet class, for edit an advertisement
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class EditAdvertisementServlet extends AbstractDatabaseServlet {
    /**
     * Edit an advertisement
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

        String title = null;
        String description = null;
        int score;
        int price;
        int numTotItem = 0;
        Date dateStart = null;
        Date dateEnd = null;
        Time timeStart = null;
        Time timeEnd = null;
        int idAdvertisement = 0;
        String emailCompany = null;
        int idType = 0;

        Advertisement advertisement;
        Message m = null;

        try{

            //TODO: Ricavarsi l'idadvertisement dell'annuncio che la company vuole modificare
            //idAdvertisement = ...
            price = Integer.parseInt(req.getParameter("price"));

            if(price<=0){
                Message msg = new Message("Input value not valid.",
                        "E1","Price not valid");
                res.setStatus(200);
                req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
            }
            score = (int) (price/3.14);

            advertisement = new Advertisement(idAdvertisement,
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
                    idType);

            // delete the booking
            //new CreateAdvertisementDatabase(getDataSource().getConnection(), advertisement).editAdvertisement();
            AdvertisementDAO.editAdvertisement(advertisement);

            /*
            m = new Message(String.format("Booking %s successfully completed. IDs:",
                    booking.getEmailTourist()));
            */

            m = new Message("Advertisement successfully edited.");

            // Show the list of booking
            req.getRequestDispatcher("/jsp/show-advertisement.jsp").forward(req, res);

        } catch (NumberFormatException ex) {
            m = new Message("Cannot edit the advertisement. " +
                    "Invalid input parameters",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot edit the advertisement: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        } catch (NamingException e) {
            //TODO fix
            e.printStackTrace();
        }
    }
}