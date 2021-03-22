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
    private Statement stmt = null;
    private ResultSet rs = null;

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
        String url = "jdbc:odbc:db2"; //TODO: edit url
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(url,"","");
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
        String date = req.getParameter("date");

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
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Read a row from DB
                // TODO: fix the parameters that must be read from the DB
                String id_advertisement = rs.getString("ID_advertisement");
                String param1 = rs.getString("param1");
                String param2 = rs.getString("param2");
                String param2 = rs.getString("param2");

                // If this row satisfies the searching criteria
                // TODO: fix the condition checking
                if (param1.equals("blabla")) {
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
            System.out.println("ResearchServlet > destroy: SQL exception");
        }
    }
}