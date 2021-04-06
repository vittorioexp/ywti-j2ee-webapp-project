package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Advertisement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for searching the User score inside the database:
 * - based on the ID
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class SearchUserScoreById {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "SELECT *\n" +
            "FROM BOOKING JOIN ADVERTISEMENT ON ADVERTISEMENT.ID_ADVERTISEMENT = BOOKING.ID_ADVERTISEMENT\n" +
            "WHERE BOOKING.email_t = ? AND BOOKING.state = 'SUCCESSFUL';";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    private final String reqIdTourist;

    /**
     * Creates a new object for searching type advertisement by idType
     *
     * @param con       the connection to the database.
     * @param reqIdTourist    the Id type of the tourist.
     */
    public SearchUserScoreById(final Connection con, final String reqIdTourist) {
        this.con = con;
        this.reqIdTourist = reqIdTourist;
    }

    /**
     * Searches the user score.
     *
     * @return a TypeAdvertisement objects matching with the Id of the type advertisement.
     * @throws SQLException if any error occurs while searching for a type advertisement.
     */
    public int searchUserScore() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Advertisement> bookings = null;
        int totalScore = 0;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqIdTourist);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                bookings.add(new Advertisement(
                        rs.getInt("ID_advertisement"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("score"),
                        rs.getInt("price"),
                        rs.getInt("num_tot_item"),
                        rs.getDate("date_start"),
                        rs.getDate("date_end"),
                        rs.getTime("time_start"),
                        rs.getTime("time_end"),
                        rs.getString("email_c"),
                        rs.getInt("ID_type")));
            }

            for (Advertisement adv : bookings) {
                totalScore = +adv.getScore();
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
        return totalScore;
    }

}
