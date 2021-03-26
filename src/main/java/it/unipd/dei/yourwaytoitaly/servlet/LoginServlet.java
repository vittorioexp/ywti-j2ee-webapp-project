package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.resource.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Servlet creating the session after verifying user login and password
 * @author Francesco Giurisato
 * @version 1.0
 * @since 1.0
 */

public class LoginServlet extends AbstractDatabaseServlet {

    private Connection con = null;

    /**
     * Establish a connection with the DB
     *
     * @param config
     *            servlet config
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     */
    public void init(ServletConfig config)
            throws ServletException {
        /*
        String dbName = "jdbc:postgresql://localhost/struts_new";
        String dbDriver = "org.postgresql.Driver";
        try {
            Class.forName(dbDriver);
            String userName = null;
            String password = null;
            Connection con = DriverManager.getConnection(dbName, userName, password);
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }*/
    }

    /**
     * Manages HTTP GET requests by showing the results (advertisements) of the search
     * This method is called when the search button is pressed!
     *
     * @param req
     *            the request from the client.
     * @param res
     *            the response from the server.
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     * @throws IOException
     *             if any problem occurs while communicating between the client
     *             and the server.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        /*
        // Get the location, the category and the desired date of booking from the HTML page
        //String user = getIdCity(req.getParameter("user"));
        //String pass = getIdType(req.getParameter("password"));
        //Date req_date = req.getParameter("date");

        // Requested advertisement
        // User ad_req = new User(req_city, req_type, req_date, req_date);

        // Display the web page
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
        out.printf("<!DOCTYPE html>%n");
        out.printf("<html lang=\"en\">%n");
        out.printf("<head>%n");
        out.printf("<meta charset=\"utf-8\">%n");
        out.printf("<title>LoginServlet web page</title>%n");
        out.printf("</head>%n");
        out.printf("<body>%n");
        out.printf("<h1>LoginServlet web page</h1>%n");
        out.printf("<hr/>%n");
        out.printf("<table border=1>%n");

        // Get data from db
        try {
            String query = "SELECT user_name FROM User WHERE user_name = ? ";
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.execute();
            while (rs.next()) {
                // Read a row from DB.Advertisement
                int city = rs.getInt("ID_city");
                int type = rs.getInt("ID_type");
                Date date_start = rs.getDate("date:start");
                Date date_end = rs.getDate("date:end");

                ad_db = new Advertisement(city, type, date_start, date_end);

                // If this row (ad) satisfies the searching criteria, display it
                if (areAdvertisementsCompatible(ad_req, ad_db)) {
                    out.println("<TR>");
                    out.println("<TD>" + city + "</TD>");
                    out.println("<TD>" + type + "</TD>");
                    out.println("<TD>" + date_start.toString() + "</TD>");
                    out.println("</TR>");
                }
            }
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
        out.printf("</table>%n");
        out.printf("</html>%n");
        out.printf("</body>%n");
        out.printf("</html>%n");
        out.flush();
        out.close();
        */

    }

    /**
     * Close the connection with the DB
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     */
    public void destroy() {
        /*try {
            con.close();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }*/
    }
}




