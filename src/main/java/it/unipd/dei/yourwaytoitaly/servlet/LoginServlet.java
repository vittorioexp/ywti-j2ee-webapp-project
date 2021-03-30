package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.SearchUserLoginDatabase;
import it.unipd.dei.yourwaytoitaly.resource.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;

/**
 * Servlet creating the session after verifying user login and password
 * @author Francesco Giurisato
 * @version 1.0
 * @since 1.0
 */

public class LoginServlet extends AbstractDatabaseServlet {



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


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handleRequest(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handleRequest(req, res);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {


        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        Enumeration<String> parameterNames = req.getParameterNames();
        String[] paramValues;

        while (parameterNames.hasMoreElements()) paramValues = req.getParameterValues(parameterNames.nextElement());
        try {
            new SearchUserLoginDatabase( getDataSource().getConnection() , paramValues[0] , paramValues[1]).createSession;
            out.close();
        }catch (NullPointerException){

            out.write( "Not valid username or password format" );

        }
        catch ( SQLException e){
            System.out.print( "[ERROR] Failed database connection" );
        }

    }

    /**
     * Close the connection with the DB
     *
     * @throws ServletException
     *            if any problem occurs while executing the servlet.
     */
    public void destroy() {
        try {
            con.close();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }
}




