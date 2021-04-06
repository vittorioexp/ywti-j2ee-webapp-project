package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for searching Feedbacks inside the database and returning them in a List:
 * receives the id relating feedback - advertisement (reqIdAdvertisement) and returns a List of Feedbacks
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public final class SearchFeedbackByAdvertisementDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "SELECT RATE, TEXT_F, DATE_F, email_t " +
                    "FROM YWTI.FEEDBACK WHERE ID_ADVERTISEMENT = ?;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The ID of the advertisement
     */
    private final int reqIdAdvertisement;

    /**
     * Creates a new object for searching feedbacks by id advertisement.
     *
     * @param con
     *            the connection to the database.
     * @param reqIdAdvertisement
     *            the ID of the chosen advertisement
     */
    public SearchFeedbackByAdvertisementDatabase(final Connection con, final int reqIdAdvertisement) {
        this.con = con;
        this.reqIdAdvertisement = reqIdAdvertisement;
    }

    /**
     * Searches feedbacks by id advertisement.
     *
     * @return a list of {@code Feedback} objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs while searching for feedbacks.
     */
    public List<Feedback> searchFeedbackByAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Feedback> feedback = new ArrayList<Feedback>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdAdvertisement);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                feedback.add(new Feedback(
                        rs.getString("email_t"),
                        rs.getInt("ID_advertisement"),
                        rs.getInt("rate"),
                        rs.getString("text_f"),
                        rs.getDate("date_f")));
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

        return feedback;
    }
}