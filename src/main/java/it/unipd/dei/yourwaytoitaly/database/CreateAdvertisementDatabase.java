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
    private static final String STATEMENT =
            "INSERT INTO Advertisement (DESCRIPTION, SCORE, PRICE, NUM_TOT_ITEM, " +
                    "DATE_START, DATE_END, TIME_START, TIME_END, email_c, ID_TYPE) " +
                    "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, pID_TYPE " +
                    "FROM Type_advertisement WHERE Type_advertisement.type = ?;";
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
            pstmt.setString(1, advertisement.getDescription());
            pstmt.setInt(2, advertisement.getScore());
            pstmt.setInt(3, advertisement.getPrice());
            pstmt.setInt(4, advertisement.getNumTotItem());
            pstmt.setDate(5, advertisement.getDateStart());
            pstmt.setDate(6, advertisement.getDateEnd());
            pstmt.setTime(7, advertisement.getTimeStart());
            pstmt.setTime(8, advertisement.getTimeEnd());
            pstmt.setString(9, advertisement.getEmailCompany());
            pstmt.setInt(10, advertisement.getIdType());

            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}