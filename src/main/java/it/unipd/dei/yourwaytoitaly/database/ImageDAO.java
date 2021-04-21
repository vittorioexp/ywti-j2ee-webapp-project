package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Image;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for:
 * <ul>
 *  <li> inserting an Image, </li>
 *  <li> deleting an Image, </li>
 *  <li> searching and returning some Images by ID_ADVERTISEMENT </li>
 * </ul>
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
                "INSERT INTO IMAGE (path_i, description_i,ID_Advertisement) " +
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
                        rs.getInt("ID_Advertisement"));
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return i;
    }

    /**
     * Searching and returning an image by ID_ADVERTISEMENT
     *
     * @return a modified Feedback
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Image> searchImageByIdAdvertisement(int idAdvertisement) throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT * FROM IMAGE WHERE ID_Advertisement = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // the results of the creation
        List<Image> listImages = new ArrayList<Image>();
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idAdvertisement);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                listImages.add(new Image(
                        rs.getInt("ID_image"),
                        rs.getString("path_i"),
                        rs.getString("description_i"),
                        rs.getInt("ID_Advertisement")));
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return listImages;
    }


}
