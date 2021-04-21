package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.TypeAdvertisement;
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
 *  <li> searching and returning a TypeAdvertisement by ID_TYPE, </li>
 *  <li> searching and returning a TypeAdvertisement by TYPE </li>
 * </ul>
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */
public class TypeAdvertisementDAO extends AbstractDAO{

    /**
     * Searches and returns a TypeAdvertisement by ID_TYPE
     *
     * @return a TypeAdvertisement objects matching
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static TypeAdvertisement searchTypeAdvertisement(int idType) throws SQLException, NamingException {
        final String STATEMENT_ID = "SELECT ID_type, type " +
                "FROM TYPE_ADVERTISEMENT " +
                "WHERE ID_type = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TypeAdvertisement typeAdvertisement = null;

        try {
            pstmt = con.prepareStatement(STATEMENT_ID);
            pstmt.setInt(1, idType);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                typeAdvertisement = new TypeAdvertisement(
                        rs.getInt("ID_type"),
                        rs.getString("type"));
            }

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return typeAdvertisement;
    }

    /**
     * Searches and returns a TypeAdvertisement by NAME (TYPE)
     *
     * @return a TypeAdvertisement objects matching
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static TypeAdvertisement searchTypeAdvertisement(String type) throws SQLException, NamingException {
        final String STATEMENT_NAME = "SELECT ID_type, type " +
                "FROM TYPE_ADVERTISEMENT " +
                "WHERE type = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TypeAdvertisement typeAdvertisement = null;

        try {

            pstmt = con.prepareStatement(STATEMENT_NAME);
            pstmt.setString(1, type);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                typeAdvertisement = new TypeAdvertisement(
                        rs.getInt("ID_type"),
                        rs.getString("type"));

            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return typeAdvertisement;
    }

    /**
     * Searches and returns a list of typeAdvertisement
     *
     * @return a City objects matching with the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<TypeAdvertisement> listTypeAdvertisement() throws SQLException, NamingException {
        final String STATEMENT_LIST = "SELECT ID_type, type " +
                "FROM TYPE_ADVERTISEMENT;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TypeAdvertisement> typeAdvertisementList = new ArrayList<TypeAdvertisement>();

        try {

            pstmt = con.prepareStatement(STATEMENT_LIST);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                typeAdvertisementList.add(new TypeAdvertisement(
                        rs.getInt("ID_type"),
                        rs.getString("type")));
            }

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return typeAdvertisementList;
    }
}
