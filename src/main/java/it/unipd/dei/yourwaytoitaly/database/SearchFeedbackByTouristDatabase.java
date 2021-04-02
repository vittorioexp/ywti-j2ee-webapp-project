package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Feedback;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for searching Feedbacks inside the database and returning them in a List:
 * receives the id relating feedback - advertisement (reqIdAdvertisement) and returns a List of Feedbacks
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */

public final class SearchFeedbackByTouristDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT RATE, TEXT_F, DATE_F, ID_ADVERTISEMENT\n" +
            "FROM FEEDBACK\n" +
            "WHERE email_t = ?;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The ID of the tourist
     */
    private final int reqIdTourist;

    /**
     * Creates a new object for searching products by category.
     *
     * @param con
     *            the connection to the database.
     * @param reqIdTourist
     *            the ID of the chosen tourist
     */
    public SearchFeedbackByTouristDatabase(final Connection con, final int reqIdTourist) {
        this.con = con;
        this.reqIdTourist = reqIdTourist;
    }

    /**
     * Searches feedback by some search parameter.
     *
     * @return a list of {@code Feedback} objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs while searching for advertisements.
     */

    public List<Feedback> SearchFeedbackByTourist() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Feedback> feedback = new ArrayList<Feedback>();

        try {
            //TODO: Dopo aver preparato le query inserire i valori corretti
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdTourist);

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
