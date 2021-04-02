package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.City;
import it.unipd.dei.yourwaytoitaly.resource.TypeAdvertisement;

import java.sql.*;

/**
 * Class for searching the Type Advertisement inside the database:
 * - based on the ID
 * - based on the textual name
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */

public class SearchTypeAdvertisementDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_ID = "SELECT ID_type, type\n" +
            "FROM TYPE_ADVERTISEMENT\n" +
            "WHERE ID_type = ?;";

    private static final String STATEMENT_NAME = "SELECT ID_type, type\n" +
            "FROM TYPE_ADVERTISEMENT\n" +
            "WHERE type = ?;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    private final int idType;
    private final String type;

    /**
     * Creates a new object for searching type advertisement by idType
     *
     * @param con       the connection to the database.
     * @param idType    the Id type of the advertisement.
     */
    public SearchTypeAdvertisementDatabase(final Connection con, final int idType) {
        this.con = con;
        this.idType = idType;
        this.type = null;
    }

    /**
     * Creates a new object for searching type advertisement by type
     *
     * @param con   the connection to the database.
     * @param type  the type of the advertisement.
     */
    public SearchTypeAdvertisementDatabase(final Connection con, final String type) {
        this.con = con;
        this.type = type;
        this.idType = -1;
    }

    /**
     * Searches bookings by tourist.
     *
     * @return a list of {@code Booking} objects matching with the Id of the advertisement.
     * @throws SQLException if any error occurs while searching for bookings.
     */

    public TypeAdvertisement searchTypeAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TypeAdvertisement typeAdvertisement = null;

        if(this.idType != -1) {

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
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                con.close();
            }

            return typeAdvertisement;
        }
        else{
            try {

                pstmt = con.prepareStatement(STATEMENT_NAME);
                pstmt.setString(1, type);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    typeAdvertisement = new TypeAdvertisement(
                            rs.getInt("ID_city"),
                            rs.getString("name"));
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                con.close();
            }

            return typeAdvertisement;
        }
    }

}
