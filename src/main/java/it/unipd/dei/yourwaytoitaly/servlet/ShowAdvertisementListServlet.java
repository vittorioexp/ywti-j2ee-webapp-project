package it.unipd.dei.yourwaytoitaly.servlet;


import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.CityDAO;
import it.unipd.dei.yourwaytoitaly.database.TypeAdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Servlet which will show:
 * - the list of Advertisements to a tourist (based on search criteria)
 * - the list of Advertisements to a company (based on emailCompany)
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class ShowAdvertisementListServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        //take the request uri
        String op = req.getRequestURI();
        //remove everything prior to /src/ (included) and use the remainder as
        //indicator for the required operation
        op = op.substring(op.lastIndexOf("src") + 4);
        List<Advertisement> listAdvertisement;
        Message m;

        switch (op){
            case "tourist/":
                int idCity;
                Date date=null;
                int idType;

                try {
                    idCity = CityDAO.searchCity(req.getParameter("city")).getIdCity();
                    date = Date.valueOf(req.getParameter("date").toString());
                    idType = TypeAdvertisementDAO.searchTypeAdvertisement(req.getParameter("typeAdvertisement")).getIdType();

                    listAdvertisement = AdvertisementDAO.searchAdvertisement(idCity,idType,date );

                    m = new Message("Request list successfully obtained.");

                    req.setAttribute("employeeList", listAdvertisement);
                    req.setAttribute("message", m);

                    req.getRequestDispatcher("/jsp/homepage.jsp").forward(req, res);

                } catch ( IllegalArgumentException e ) {
                    ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(ec.getHTTPCode());
                    req.setAttribute("message", new Message("Cannot search the advertisement.: unexpected error while accessing the database.",
                            "E200", "Unespected error."));
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
                }catch (NumberFormatException ex) {
                    m = new Message("Cannot search the advertisement. " +
                            "Invalid input parameters",
                            "E100", ex.getMessage());
                } catch (SQLException ex) {
                    m = new Message("Cannot search the advertisement.: unexpected error while accessing the database.",
                            "E200", ex.getMessage());
                } catch (NamingException ex) {
                    m = new Message("Cannot search the advertisement.: Naming error.",
                            "E200", ex.getMessage());
                }
                break;
            case "company/":
                // activate a session to keep the user data
                HttpSession session = req.getSession();

                // insert in the session the email
                String email = session.getAttribute("email").toString();

                try {

                    listAdvertisement = AdvertisementDAO.searchAdvertisement(email);
                    m = new Message("Request list successfully obtained.");

                    req.setAttribute("listAdvertisement", listAdvertisement);

                    req.getRequestDispatcher("/jsp/show-profile.jsp").forward(req, res);

                }catch (NumberFormatException ex) {
                    m = new Message("Cannot search the advertisement. " +
                            "Invalid input parameters",
                            "E100", ex.getMessage());
                } catch (SQLException ex) {
                    m = new Message("Cannot search the advertisement.: unexpected error while accessing the database.",
                            "E200", ex.getMessage());
                } catch (NamingException ex) {
                    m = new Message("Cannot search the advertisement.: Naming error.",
                            "E200", ex.getMessage());
                }
                break;
            default:
                // the requested operation is unknown
                ErrorCode ec = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", new Message("Cannot search the advertisement.: unexpected error while accessing the database.",
                        "E200", "Unespected error."));
                req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }

    }

}




