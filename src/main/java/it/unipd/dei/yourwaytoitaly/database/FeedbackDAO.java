package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for:
 * <ul>
 *  <li> inserting a Feedback, </li>
 *  <li> editing some parameters of a Feedback, </li>
 *  <li> searching and returning some Feedback by ID_ADVERTISEMENT, </li>
 *  <li> searching and returning some Feedback by EMAIL_T </li>
 * </ul>
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class FeedbackDAO extends AbstractDAO{

    /**
     * Inserts a new feedback.
     *
     * @return the just created feedback
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static Feedback createFeedback(Feedback feedback) throws SQLException, NamingException {
        final String STATEMENT =
                "INSERT INTO FEEDBACK (email_t, ID_Advertisement, rate, text_f, date_f) " +
                        "VALUES (?, ?, ?, ?, ?) RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        Feedback f = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, feedback.getEmailTourist());
            pstmt.setInt(2, feedback.getIdAdvertisement());
            pstmt.setInt(3, feedback.getRate());
            pstmt.setString(4, feedback.getText());
            pstmt.setDate(5, feedback.getDate());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                f = new Feedback(
                        rs.getString("email_t"),
                        rs.getInt("ID_advertisement"),
                        rs.getInt("rate"),
                        rs.getString("text_f"),
                        rs.getDate("date_f"));
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return f;
    }

    /**
     * Searches and returns a Feedback both by EMAIL_T and ID_ADVERTISEMENT
     *
     * @return a Booking objects matching with the criteria
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static Feedback searchFeedback(String emailTourist, int idAdvertisement)
            throws SQLException, NamingException {
        List<Feedback> feedback = searchFeedbackByAdvertisement(idAdvertisement);
        for (Feedback f : feedback) {
            if (f.getEmailTourist().equals(emailTourist)) {
                return f;
            }
        }
        return null;
    }


    /**
     * Searches and returns some Feedback by ID_ADVERTISEMENT
     *
     * @return a list of {@code Feedback} objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Feedback> searchFeedbackByAdvertisement(int reqIdAdvertisement) throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT * FROM FEEDBACK WHERE id_advertisement = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
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
                        rs.getInt("id_advertisement"),
                        rs.getInt("rate"),
                        rs.getString("text_f"),
                        rs.getDate("date_f")));
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return feedback;
    }


    /**
     * Searches and returns some Feedback by EMAIL_T
     *
     * @return a list of {@code Feedback} objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Feedback> searchFeedbackByTourist(String reqIdTourist) throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT RATE, TEXT_F, DATE_F, ID_ADVERTISEMENT " +
                        "FROM FEEDBACK WHERE email_t = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Feedback> feedback = new ArrayList<Feedback>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqIdTourist);

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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return feedback;
    }
}
