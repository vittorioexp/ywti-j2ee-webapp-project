package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.resource.Tourist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for searching a User inside the database and returning it as an User object:
 * receives username / email + password (reqUser / reqEmail + reqPassword) and checks if the pair is present in the DB.
 * If so, it returns the corresponding Company / Tourist object, otherwise it returns null.
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */


public final class SearchUserLoginDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = ""; //TODO: query needed

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The ID of the advertisement
     */
    private final String reqEmail;
    private final String reqPassword;


    /**
     * Creates a new object for searching tourist.
     *
     * @param con
     *            the connection to the database.
     * @param reqEmail
     *            the ID of the tourist
     * @param reqPassword
     *            the password of the user
     */

    public SearchUserLoginDatabase(final Connection con, final String reqEmail, final String reqPassword) {
        this.con = con;
        this.reqEmail = reqEmail;
        this.reqPassword = reqPassword;
    }

    /**
     * Searches user by some search parameter.
     *
     * @return a tourist objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs while searching for advertisements.
     */

    public User SearchUserLogin() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        User user = null;

        try {
            //TODO: Dopo aver preparato le query inserire i valori corretti
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqEmail);
            pstmt.setString(2, reqPassword);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = new Tourist(
                        rs.getString("email_t"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getInt("ID_city"),
                        rs.getString("surname"),
                        rs.getDate("birth_date"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return user;
    }
}