package it.unipd.dei.yourwaytoitaly.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

/**
* Database Abstract Class manages basic connection to the Database
*
* @author Francesco Giurisato
*
* */

public class AbstractDatabaseServlet extends HttpServlet{

    private DataSource ds;

    /**
     * Creating Initial context
     *
     * @param config  ServletConfig configuration object
     * @exception ServletException if connection to db fails
     * */

    public void init(ServletConfig config) throws ServletException {

        InitialContext ct;

        try {
            ct = new InitialContext();
            ds = (DataSource) ct.lookup ("java:comp/env/jdbc/ytwiDb") // TODO : changing datase context name

        } catch (NamingException e) {
            ds = null;

            throw new ServletException (String.format("[ERROR_AbstractDatabase] Creating Database connection " + e.getMessage()));

        }

    }

    /**
     * Destroy Datasource handler
     *
     * */

    public void destroy (){

        ds = null;

    }

    /**
     * Method cleaning and closing previous connections
     *
     * @param st  PreparedStatement object
     * @param result  ResultSet object
     * @param conn  Connection object
     * @exception ServletException if connection to db fails
     * */

     static void cleaningOperations(PreparedStatement st, ResultSet result, Connection conn) throws SQLException {
        if (st!=null) {
            st.close();
        }
        if (result!=null) {
            result.close();
        }
        if (conn!=null) {
            conn.close();
        }
    }

    /**
     * Method cleaning and closing previous connections, without the parameter result. Calling the other one
     * and passing result = null
     *
     * @param st  PreparedStatement object
     * @param conn  Connection object
     * @exception ServletException if connection to db fails
     * */

    static void cleaningOperations(PreparedStatement st, Connection conn) throws SQLException {
        cleaningOperations(st, null, conn);

    }


}
