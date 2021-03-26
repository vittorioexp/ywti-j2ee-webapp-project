package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Feedback;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * Class for searching Feedbacks inside the database and returning them in a List:
 * receives the id relating feedback - advertisement (reqIdAdvertisement) and returns a List of Feedbacks
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchFeedbackDatabase {
    private static final String STATEMENT = ""; //TODO: query needed
    private final Connection con;
    private final int reqIdAdvertisement;

    public SearchFeedbackDatabase(final Connection con, final int reqIdAdvertisement) {
        this.con = con;
        this.reqIdAdvertisement = reqIdAdvertisement;
    }

    public List<Feedback> SearchFeedback() throws SQLException {
        //TODO: write the function body
        return null;
    }
}