//TODO: package name?

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Servlet which will show the results (advertisements) of the search
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class ResearchServlet extends HttpServlet {

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
        //TODO: add postgresql.jar at the following location: tomcat_home/webapps/<project_name>/WEB-INF/lib
        // Without this jar file --> ClassNotFoundException
        String dbName = "jdbc:postgresql://localhost/struts_new"; //TODO: edit dbName
        String dbDriver = "org.postgresql.Driver";
        try {
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName, userName, password);
            System.out.println("ResearchServlet > init(): Got Connection");
        } catch (ClassNotFoundException ex) {
            System.out.println("ResearchServlet > init(): driver not found!");
            return;
        } catch (SQLException ex) {
            System.out.println("ResearchServlet > init(): connection with the database is failed!");
            return;
        }
    }

    /**
     * Manages HTTP POST requests by showing the results (advertisements) of the search
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
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Get the location, the category and the desired date of booking from the HTML page
        String location = req.getParameter("location");
        String category = req.getParameter("category");
        String date_of_availability = req.getParameter("date");

        //TODO: fix the webpage layout
        // Display the web page
        out.printf("<!DOCTYPE html>%n");
        out.printf("<html lang=\"en\">%n");
        out.printf("<head>%n");
        out.printf("<meta charset=\"utf-8\">%n");
        out.printf("<title>HelloWorld Form Post Servlet  Response</title>%n");
        out.printf("</head>%n");
        out.printf("<body>%n");
        out.printf("<h1>HelloWorld Form Post Servlet Response</h1>%n");
        out.printf("<hr/>%n");
        out.printf("<table border=1>%n");

        // Get data from DB and display it
        try {
            String query = "select * from Advertisement"; //TODO: Fix query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Read a row from DB
                // TODO: fix the parameters that must be read from the DB
                // i need to know the location, the category and the date of availability for each advertisement
                String ad_location = rs.getString("ID_city");
                String ad_category = getTypeOfAdvertisement(rs.getString("ID_type"));
                String ad_date = rs.getString("date");

                String id_advertisement = rs.getString("ID_advertisement");
                String id_user = rs.getString("ID_user");
                String id_type = rs.getString("ID_type");
                String score = rs.getString("score");
                String price = rs.getString("price");
                String num_item = rs.getString("num_item");



                // If this row satisfies the searching criteria
                // TODO: fix the condition checking
                if (advertisementIsGood(ad_location, ad_category, ad_date)) {
                    // Display the ad
                    out.println("<TR>");
                    out.println("<TD>" + param1 + "</TD>");
                    out.println("<TD>" + param2 + "</TD>");
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            out.println("ResearchServlet > doPost(): SQL exception");
        }
        out.printf("</html>%n");
        out.printf("</body>%n");
        out.printf("</html>%n");
        out.flush();
        out.close();

        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
    }

    /**
     * Close the connection with the DB
     *
     * @param config
     *            servlet config
     *
     * @throws ServletException
     *             if any problem occurs while executing the servlet.
     */
    public void destroy() {
        try {
            con.close();
        } catch(SQLException ex) {
            System.out.println("ResearchServlet > destroy(): SQL exception");
        }
    }

    private String getTypeOfAdvertisement(String ID_type) {
        try {
            String query = "SELECT name FROM Type_advertisement WHERE ID_type = " + ID_type; //TODO: Fix query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

        } catch(Exception e) {
            System.out.println("ResearchServlet > getTypeOfAdvertisement(): SQL exception");
        }
    }

    private boolean advertisementIsGood(String loc, String type, String date) {

    }


}

public class Advertisement {
    private String location = null;
    private String category = null;
    private String date = null;

    public Advertisement(String location, String category, String date) {
        this.location=location;
        this.category=category;
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public String getCategory() {
        return category;
    }
    public String getDate() {
        return date;
    }
    public boolean isCompatibleWith(Advertisement other_ad) {
        if (other_ad.getLocation().equalsIgnoreCase(location) &&
            other_ad.getCategory().equalsIgnoreCase(category) &&
            other_ad.getDate().equalsIgnoreCase(date)) {
            return true;
        }
        return false;
    }
}
