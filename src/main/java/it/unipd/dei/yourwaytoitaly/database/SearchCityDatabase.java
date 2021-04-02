package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.City;

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

public class SearchCityDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_ID = "SELECT ID_city, name\n" +
            "FROM CITY\n" +
            "WHERE ID_city = ?;";

    private static final String STATEMENT_NAME = "SELECT ID_city, name\n" +
            "FROM CITY\n" +
            "WHERE name = ?;";

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
     * Creates a new object for searching city by ID
     *
     * @param con       the connection to the database.
     * @param idType    the Id type of the city.
     */
    public SearchCityDatabase(final Connection con, final int idType) {
        this.con = con;
        this.idType = idType;
        this.type = null;
    }

    /**
     * Creates a new object for searching city by name
     *
     * @param con   the connection to the database.
     * @param type  the name of the city.
     */
    public SearchCityDatabase(final Connection con, final String type) {
        this.con = con;
        this.idType = -1;
        this.type = type;
    }

    /**
     * Searches bookings by tourist.
     *
     * @return a City objects matching with the parameter.
     * @throws SQLException if any error occurs while searching for bookings.
     */

    public City searchTypeAdvertisement() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        City city = null;

        if(this.idType != -1) {

            try {

                pstmt = con.prepareStatement(STATEMENT_ID);
                pstmt.setInt(1, idType);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    city = new City(
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

            return city;
        }
        else{
            try {

                pstmt = con.prepareStatement(STATEMENT_NAME);
                pstmt.setString(1, type);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    city = new City(
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

            return city;
        }
    }

}
