package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for inserting an Image inside the database (i.e. at the moment of inserting an Advertisement)
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateImageDatabase {
    private static final String STATEMENT = ""; //TODO: query is needed here
    private final Connection con;
    private final Image image;
    public CreateImageDatabase(final Connection con, final Image image) {
        this.con = con;
        this.image = image;
    }
    public void CreateImage() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, image.getIdUser());
            pstmt.setString(2, image.getPath());
            pstmt.setInt(3, image.getIdAdvertisement());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}