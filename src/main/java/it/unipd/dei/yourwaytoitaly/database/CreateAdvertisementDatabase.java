package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
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
    private static final String STATEMENT = "";
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
            pstmt.setDate(8, advertisement.getDateStart());
            pstmt.setDate(9, advertisement.getDateEnd());
            pstmt.setTime(10, advertisement.getTimeStart());
            pstmt.setTime(11, advertisement.getTimeEnd());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}