package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.Advertisement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for searching Advertisements inside the database and returning them in a List:
 * receives the search parameters requested by the user and returns a List containing
 * all the Advertisement compatible with what the user requested.
 * If there are no compatible Advertisement, it returns null.
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public final class SearchAdvertisementById {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "SELECT * " +
                    "FROM YWTI.ADVERTISEMENT " +
                    "WHERE ADVERTISEMENT.ID_ADVERTISEMENT = ? ;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    private final int reqIdAdvertisement;

    /**
     * Creates a new object for searching advertisementS by some search parameters.
     *
     * @param con
     *              the connection to the database.
     * @param reqIdAdvertisement
     *              the request ID
     *
     */

    public SearchAdvertisementById(final Connection con, final int reqIdAdvertisement) {
        this.con = con;
        this.reqIdAdvertisement = reqIdAdvertisement;
    }

    public Advertisement searchAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        Advertisement advertisement=null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdAdvertisement);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                advertisement = new Advertisement(
                        rs.getInt("ID_advertisement"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("score"),
                        rs.getInt("price"),
                        rs.getInt("num_tot_item"),
                        rs.getDate("date_start"),
                        rs.getDate("date_end"),
                        rs.getTime("time_start"),
                        rs.getTime("time_end"),
                        rs.getString("email_c"),
                        rs.getInt("ID_type"));
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

        return advertisement;
    }
}