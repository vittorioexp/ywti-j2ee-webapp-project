package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.resource;
import it.unipd.dei.yourwaytoitaly.database;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;

/**
 * Servlet class, to be written
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class RegisterServlet extends AbstractDatabaseServlet {

    /**
     * Manages HTTP GET requests for login
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

    /**
     * Manages HTTP POST requests for login
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


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handleRequest(req, res);
    }

    /**
     * Manages HTTP GET and POST requests for login
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

    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {


        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        Enumeration<String> parameterNames = req.getParameterNames();
        String[] paramValues;

        while (parameterNames.hasMoreElements()) paramValues = req.getParameterValues(parameterNames.nextElement());

        String email = paramValues [0];
        String password = paramValues [1];
        // TODO : scanning parameter to create the object Tourist / Company
        Tourist t = new Tourist ( email , password , "ciccio", "via dei desideri" , "12233445" ,
                1 , "caputo", new Date());

        try {


            User u = new CreateUserDatabase( getDataSource().getConnection() , paramValues[0] , paramValues[1]).SearchUserLogin();
            // TODO : manage login

            if ( u == null )
                out.write( "Error registering the account" );
            else
                out.write( "Ready to start your way to Italy dear " + u.getName() );

            out.close();
        }catch (NullPointerException e){

            out.write( "Not valid username or password format" );

        }
        catch ( SQLException e){
            System.out.print( "[ERROR] Failed database connection" );
        }

    }

}