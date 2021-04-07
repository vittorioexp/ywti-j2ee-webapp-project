package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Image;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for:
 * - inserting an Image
 * - deleting an Image
 * - searching and returning some Images by ID_ADVERTISEMENT
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */
public class ImageDAO extends AbstractDAO{

    /**
     * Inserts a new image.
     *
     * @return the just created image
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static Image createImage(Image image) throws SQLException, NamingException {
        final String STATEMENT =
                "INSERT INTO YWTI.IMAGE (path_i, description_i,ID_Advertisement) " +
                        "VALUES (?, ?, ?) RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        Image i = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
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
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return i;
    }

    /**
     * Delete some images by ID_ADVERTISEMENT
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static void deleteImageByIdAdvertisement(int idAdvertisement) throws SQLException, NamingException {
        //TODO: write frunction body
    }

    /**
     * searching and returning an image by ID_ADVERTISEMENT
     *
     * @return a modified Feedback
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Image> SearchImageByIdAdvertisement(int idAdvertisement) throws SQLException, NamingException {
        //TODO: write frunction body
        return null;
    }


}
