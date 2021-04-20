package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract Database Servlet that manages basic connection to the Database
 *
 * @author Francesco Giurisato
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */


public class AbstractDatabaseServlet extends HttpServlet {

    /**
     * The connection pool to the database.
     */
    private DataSource ds = null;

    /**
     * Gets the {@code DataSource} for managing the connection pool to the database.
     *
     * @param config a {@code ServletConfig} object containing the servlet's
     *               configuration and initialization parameters.
     * @throws ServletException if an exception has occurred that interferes with the servlet's normal operation
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        InitialContext ct;

        try {
            ct = new InitialContext();
            ds = (DataSource) ct.lookup("java:/comp/env/jdbc/YWTI");

        } catch (NamingException e) {
            ds = null;
            throw new ServletException(
                    String.format("[ERROR_AbstractDatabase] Creating Database connection " + e.getMessage()));

        }

    }

    public void sendError(HttpServletResponse res, ErrorCode ec) throws IOException {
        res.setStatus(ec.getHTTPCode());
        //res.getWriter().write(ec.toJSON().toString());
    }

    /**
     * Releases the {@code DataSource} for managing the connection pool to the database.
     */
    public void destroy (){
        ds = null;
    }

    /**
     * Returns the {@code DataSource} for managing the connection pool to the database.
     *
     * @return the {@code DataSource} for managing the connection pool to the database
     */
    public DataSource getDataSource(){
        return ds;
    }

    /**
     * Method cleaning and closing previous connections
     *
     * @param st  PreparedStatement object
     * @param result  ResultSet object
     * @param conn  Connection object
     * @exception ServletException if connection to db fails
     */
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
