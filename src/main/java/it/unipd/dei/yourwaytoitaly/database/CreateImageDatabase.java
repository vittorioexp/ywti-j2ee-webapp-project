package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class for inserting an Image inside the database (i.e. at the moment of inserting an Advertisement)
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */



public final class CreateImageDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "INSERT INTO YWTI.IMAGE (path_i, description_i,ID_Advertisement) " +
            "VALUES (?, ?, ?);";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The image to create
     */
    private final Image image;

    /**
     * Creates a new object for creating images.
     *
     * @param con
     *            the connection to the database.
     * @param image
     *            the image to create.
     */
    public CreateImageDatabase(final Connection con, final Image image) {
        this.con = con;
        this.image = image;
    }

    /**
     * Creates a new image.
     *
     * @return the just created image
     *
     * @throws SQLException
     *             if any error occurs while creating images.
     */
    public Image CreateImage() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        Image i = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            //pstmt.setInt(1, image.getIdImage());
            pstmt.setString(1, image.getPath());
            pstmt.setString(2, image.getDescription());
            pstmt.setInt(3, image.getIdAdvertisement());

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