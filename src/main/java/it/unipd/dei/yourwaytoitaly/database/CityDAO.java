package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.City;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for:
 * - searching and returning a city by ID_CITY
 * - searching and returning a city by NAME
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class CityDAO extends AbstractDAO{

    /**
     * searches and returns a city by ID_CITY
     *
     * @return a City objects matching with the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static City searchCity(int idType) throws SQLException, NamingException {
        final String STATEMENT_ID = "SELECT ID_city, name " +
                "FROM CITY " +
                "WHERE ID_city = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        City city = null;

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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return city;
    }

    /**
     * searches and returns a city by NAME (TYPE)
     *
     * @return a City objects matching with the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static City searchCity(String type) throws SQLException, NamingException {
        final String STATEMENT_NAME = "SELECT ID_city, name " +
                "FROM CITY " +
                "WHERE name = ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        City city = null;

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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return city;
    }

    /**
     * searches and returns a list of city
     *
     * @return a City objects matching with the parameter.
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<City> listCities() throws SQLException, NamingException {
        final String STATEMENT_LIST = "SELECT ID_city, name " +
                "FROM CITY;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<City> cityList = null;

        try {

            pstmt = con.prepareStatement(STATEMENT_LIST);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                cityList.add(new City(
                        rs.getInt("ID_city"),
                        rs.getString("name")));
            }

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return cityList;
    }
}
