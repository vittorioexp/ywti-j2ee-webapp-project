package it.unipd.dei.yourwaytoitaly.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract DAO class to be extended
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */
public class AbstractDAO {

    /**
     * Cleans the operations
     *
     * @param stmnt     the prepared statement
     * @param result    the result
     * @param conn      the connection to the database
     * @throws SQLException if any error occurs while accessing the database
     */
    static void cleaningOperations(PreparedStatement stmnt, ResultSet result, Connection conn) throws SQLException {
        if (stmnt!=null) {
            stmnt.close();
        }
        if (result!=null) {
            result.close();
        }
        if (conn!=null) {
            conn.close();
        }
    }

    /**
     * Cleans the operations
     *
     * @param stmnt     the prepared statement
     * @param conn      the connection to the database
     * @throws SQLException if any error occurs while accessing the database
     */
    static void cleaningOperations(PreparedStatement stmnt, Connection conn) throws SQLException {
        cleaningOperations(stmnt, null, conn);
    }
}