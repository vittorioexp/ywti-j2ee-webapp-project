package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
     * Update an advertisement
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

    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

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

            // check if idAdvertisement is present in the session
            if (session.getAttribute("idAdvertisement") == null) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message("Advertisement not found.",
                        ec.getErrorCode(),"The ID of the advertisement was not found. ");
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
            }

            // receive idAdvertisement from the session
            idAdvertisement = (int) session.getAttribute("idAdvertisement");

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

            req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Cannot edit the advertisement. ",
                    ec.getErrorCode(), ex.getMessage());
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/edit-advertisement.jsp").forward(req, res);
        }
    }
}