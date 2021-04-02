package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.resource.Feedback;

import java.sql.*;
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

public final class SearchFeedbackDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = ""; //TODO: query needed

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The category of the product
     */
    private final int reqIdTourist;
    private final int reqIdCompany;

    /**
     * Creates a new object for searching products by category.
     *
     * @param con
     *            the connection to the database.
     * @param reqIdTourist
     *            the ID of the chosen tourist
     */
    public SearchFeedbackDatabase(final Connection con, final int reqIdTourist) {
        this.con = con;
        this.reqIdTourist = reqIdTourist;
        this.reqIdCompany =-1;
    }

    /**
     * Creates a new object for searching products by category.
     *
     * @param con
     *            the connection to the database.
     * @param reqIdCompany
     *            the ID of the chosen company
     */
    public SearchFeedbackDatabase(final Connection con, final int reqIdCompany) {
        this.con = con;
        this.reqIdCompany = reqIdCompany;
        this.reqIdTourist =-1;
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

    public List<Feedback> SearchFeedbackByAdvertisement(final int reqIdAdvertisement) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Feedback> feedback = new ArrayList<Feedback>();

        try {

            //TODO: Inserire la query per la ricerca utilizzando reqIdAdvertisement
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