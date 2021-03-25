package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for inserting an Advertisement inside the database
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateAdvertisementDatabase {
    private static final String STATEMENT = "INSERT INTO YWTIDB.Advertisement " +
            "(ID_advertisement, ID_user, ID_type, description, score, price, num_tot_item, date:start, date:end) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final Connection con;
    private final Advertisement advertisement;
    public CreateAdvertisementDatabase(final Connection con, final Advertisement advertisement) {
        this.con = con;
        this.advertisement = advertisement;
    }
    public void createAdvertisement() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, advertisement.getIdAdvertisement());
            pstmt.setInt(2, advertisement.getIdUser());
            pstmt.setInt(3, advertisement.getIdType());
            pstmt.setString(4, advertisement.getDescription());
            pstmt.setInt(5, advertisement.getScore());
            pstmt.setInt(6, advertisement.getPrice());
            pstmt.setInt(7, advertisement.getNumTotItem());
            pstmt.setTimestamp(8, advertisement.getDateStart());
            pstmt.setTimestamp(9, advertisement.getDateEnd());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}