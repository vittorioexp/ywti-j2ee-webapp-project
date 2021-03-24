package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for accessing the database, copies to/from application data structures,
 * ensures to release the connection, and delegates the management of any exception to the caller class
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateAdvertisementDatabase {
    private static final String STATEMENT_ADVERTISEMENT = "INSERT INTO YWTIDB.Advertisement " +
            "(ID_advertisement, ID_user, ID_type, description, score, price, num_tot_item, date:start, date:end) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final Connection con;
    private final Advertisement advertisement;
    public CreateUserDatabase(final Connection con, final Advertisement advertisement) {
        this.con = con;
        this.advertisement = advertisement;
    }
    public void createAdvertisement() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT_ADVERTISEMENT);
            pstmt.setInt(1, user.getIdAdvertisement());
            pstmt.setInt(2, user.getIdUser());
            pstmt.setInt(3, user.getIdType());
            pstmt.setString(4, user.getDescription());
            pstmt.setInt(5, user.getScore());
            pstmt.setInt(6, user.getPrice());
            pstmt.setInt(7, user.getNumTotItem());
            pstmt.setDate(8, user.getDateStart());
            pstmt.setDate(9, user.getDateEnd());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}