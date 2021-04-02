package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class for inserting a Feedback inside the database
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */


public final class CreateFeedbackDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "INSERT INTO YWTI.FEEDBACK (email_t, ID_Advertisement, rate, text_f, date_f) " +
            "VALUES (?, ?, ?, ?, ?) RETURNING *;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The feedback to create
     */
    private final Feedback feedback;

    /**
     * Creates a new object for creating feedbacks.
     *
     * @param con
     *            the connection to the database.
     * @param feedback
     *            the feedback to create.
     */
    public CreateFeedbackDatabase(final Connection con, final Feedback feedback) {
        this.con = con;
        this.feedback = feedback;
    }

    /**
     * Creates a new feedback.
     *
     * @return the just created feedback
     *
     * @throws SQLException
     *             if any error occurs while creating feedbacks.
     */
    public Feedback CreateFeedback() throws SQLException {
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
        }finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
        return f;
    }
}