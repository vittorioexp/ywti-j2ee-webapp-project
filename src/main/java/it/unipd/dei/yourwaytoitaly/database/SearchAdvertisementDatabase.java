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
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */

public final class SearchAdvertisementDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT =
            "SELECT ADVERTISEMENT.TITLE, ADVERTISEMENT.DESCRIPTION, ADVERTISEMENT.SCORE, ADVERTISEMENT.PRICE, ADVERTISEMENT.NUM_TOT_ITEM " +
                    "FROM YWTI.ADVERTISEMENT " +
                    "JOIN YWTI.COMPANY ON COMPANY.EMAIL_C = ADVERTISEMENT.EMAIL_C " +
                    "JOIN YWTI.CITY ON CITY.ID_CITY = COMPANY.ID_CITY " +
                    "JOIN YWTI.TYPE_ADVERTISEMENT ON TYPE_ADVERTISEMENT.ID_Type = ADVERTISEMENT.ID_Type " +
                    "WHERE CITY.NAME = ? AND " +
                    "TYPE_ADVERTISEMENT.TYPE = ? AND " +
                    "DATE_START >= ?;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    private final int reqIdCity;
    private final int reqIdType;
    private final Date reqDate;

    /**
     * Creates a new object for searching advertisementS by some search parameters.
     *
     * @param con
     *              the connection to the database.
     * @param reqIdCity
     *              the ID of the city
     * @param reqIdType
     *              the ID of the advertisement type
     * @param reqDate
     *              the date in which the advertisement starts
     *
     */
    public SearchAdvertisementDatabase(final Connection con, final int reqIdCity, final int reqIdType,
                                       final Date reqDate) {
        this.con = con;
        this.reqIdCity = reqIdCity;
        this.reqIdType = reqIdType;
        this.reqDate = reqDate;
    }

    /**
     * Searches advertisements by some search parameters.
     *
     * @return a list of {@code Advertisement} objects matching the parameters.
     *
     * @throws SQLException
     *             if any error occurs while searching for advertisements.
     */
    public List<Advertisement> searchAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Advertisement> advertisements = new ArrayList<Advertisement>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdCity);
            pstmt.setInt(2, reqIdType);
            pstmt.setDate(3, reqDate);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                advertisements.add(new Advertisement(
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
                        rs.getInt("ID_type")));
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

        return advertisements;
    }
}