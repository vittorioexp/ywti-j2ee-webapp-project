package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void CreateFeedback() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, feedback.getIdUser());
            pstmt.setInt(2, feedback.getIdAdvertisement());
            pstmt.setInt(3, feedback.getRate());
            pstmt.setString(4, feedback.getText());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}