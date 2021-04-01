package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import it.unipd.dei.yourwaytoitaly.resource.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class for inserting a Feedback inside the database
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateFeedbackDatabase {
    private static final String STATEMENT = ""; //TODO: query is needed here
    private final Connection con;
    private final Feedback feedback;
    public CreateFeedbackDatabase(final Connection con, final Feedback feedback) {
        this.con = con;
        this.feedback = feedback;
    }
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