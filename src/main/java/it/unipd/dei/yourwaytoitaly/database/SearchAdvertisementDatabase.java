package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.Advertisement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for searching Advertisements inside the database and returning them in a List:
 * receives the search parameters requested by the user for an advertisement
 * and returns a List containing all the Advertisement compatible with what the user requested.
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
            "SELECT ADVERTISEMENT.DESCRIPTION, ADVERTISEMENT.SCORE, ADVERTISEMENT.PRICE, ADVERTISEMENT.NUM_TOT_ITEM\n" +
                    "\tFROM YWTI.ADVERTISEMENT\n" +
                    "\t\tJOIN YWTI.COMPANY ON COMPANY.EMAIL_C = ADVERTISEMENT.EMAIL_C\n" +
                    "\t\tJOIN YWTI.CITY ON CITY.ID_CITY = COMPANY.ID_CITY\n" +
                    "\t\tJOIN YWTI.TYPE_ADVERTISEMENT ON TYPE_ADVERTISEMENT.ID_Type = ADVERTISEMENT.ID_Type\n" +
                    "\tWHERE \tCITY.NAME = ? AND\n" +
                    "\t\t\tTYPE_ADVERTISEMENT.TYPE = ? AND \n" +
                    "\t\t\tDATE_START >= ? AND\n" +
                    "\t\t\tDATE_END <= ? AND\n" +
                    "\t\t\tTIME_START >= ? AND\n" +
                    "\t\t\tTIME_END <= ?;";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Some search parameters
     */
    //TODO: 1) fix these search parameters
    private final int reqIdCity;
    private final int reqIdType;
    private final Date reqDate;
    private final Time reqTime;

    //TODO: 3) fix the comment below to match with the arguments of the constructor
    /**
     * Creates a new object for searching advertisement by some search parameters.
     *
     * @param con
     *            the connection to the database.
     */
    //TODO: 2) fix the constructor (change search parameters)
    public SearchAdvertisementDatabase(final Connection con, final int reqIdCity, final int reqIdType,
                                       final Date reqDate, final Time reqTime) {
        this.con = con;
        this.reqIdCity = reqIdCity;
        this.reqIdType = reqIdType;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
    }

    /**
     * Searches advertisements by some search parameters.
     *
     * @return a list of {@code Advertisement} objects matching the parameters.
     *
     * @throws SQLException
     *             if any error occurs while searching for advertisements.
     */
    public List<Advertisement> SearchAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Advertisement> advertisements = new ArrayList<Advertisement>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            //pstmt.setString(1, category);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                /*advertisements.add(new Advertisement(
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getString("ageRange"),
                        rs.getDouble("price")));  */
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