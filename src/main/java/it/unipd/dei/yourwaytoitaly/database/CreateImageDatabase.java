package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.Booking;
import it.unipd.dei.yourwaytoitaly.resource.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Image CreateImage() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        Image i = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, image.getIdImage());
            pstmt.setString(2, image.getPath());
            pstmt.setString(3, image.getDescription());
            pstmt.setInt(4, image.getIdAdvertisement());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                i = new Image(
                        rs.getInt("ID_image"),
                        rs.getString("path_i"),
                        rs.getString("description_i"),
                        rs.getInt("ID_advertisement"));
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
        return i;
    }
}