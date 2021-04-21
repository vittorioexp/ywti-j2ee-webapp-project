package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.Advertisement;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for:
 * <ul>
 *  <li> inserting an Advertisement, </li>
 *  <li> editing some parameters of an Advertisement, </li>
 *  <li> searching an Advertisement by ID_ADVERTISEMENT, </li>
 *  <li> searching and returning some Advertisements by ID_CITY, ID_TYPE and DATE_START  </li>
 * </ul>
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */
public class AdvertisementDAO extends AbstractDAO{

    /**
     * Inserts a new advertisement.
     *
     * @return the just created advertisement
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     *
     */
    public static Advertisement createAdvertisement(Advertisement advertisement) throws SQLException, NamingException {
        final String STATEMENT =
                "INSERT INTO Advertisement (TITLE, DESCRIPTION, SCORE, PRICE, NUM_TOT_ITEM, DATE_START, DATE_END, TIME_START, TIME_END, email_c, ID_TYPE)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the creation
        Advertisement a = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, advertisement.getTitle());
            pstmt.setString(2, advertisement.getDescription());
            pstmt.setInt(3, advertisement.getScore());
            pstmt.setInt(4, advertisement.getPrice());
            pstmt.setInt(5, advertisement.getNumTotItem());
            pstmt.setDate(6, advertisement.getDateStart());
            pstmt.setDate(7, advertisement.getDateEnd());
            pstmt.setTime(8, advertisement.getTimeStart());
            pstmt.setTime(9, advertisement.getTimeEnd());
            pstmt.setString(10, advertisement.getEmailCompany());
            pstmt.setInt(11, advertisement.getIdType());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                a = new Advertisement(
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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return a;
    }

    /**
     * Edits some parameters of an Advertisement
     *
     * @return the just edited advertisement
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static Advertisement editAdvertisement(Advertisement advertisement) throws SQLException, NamingException {
        final String STATEMENT_EDIT =
                "UPDATE ADVERTISEMENT SET price = ? , score = ? , num_tot_item = ? , " +
                        "title = ? , description = ? , date_end = ? , date_start = ? , " +
                        "time_start = ? , time_end = ?" +
                        "WHERE ID_advertisement = ? RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Advertisement adv = null;

        try {
            pstmt = con.prepareStatement(STATEMENT_EDIT);
            pstmt.setInt(1, advertisement.getPrice());
            pstmt.setInt(2, advertisement.getScore());
            pstmt.setInt(3, advertisement.getNumTotItem());
            pstmt.setString(4, advertisement.getTitle());
            pstmt.setString( 5 , advertisement.getDescription());
            pstmt.setDate( 6 , advertisement.getDateEnd());
            pstmt.setDate( 7 , advertisement.getDateStart());
            pstmt.setTime( 8 , advertisement.getTimeStart());
            pstmt.setTime( 9 , advertisement.getTimeEnd());
            pstmt.setInt(10, advertisement.getIdAdvertisement());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                adv = new Advertisement(
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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return adv;
    }

    /**
     * Delete an Advertisement
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static void deleteAdvertisement(int idAdvertisement) throws SQLException, NamingException {
        final String STATEMENT_EDIT =
                "UPDATE ADVERTISEMENT SET num_tot_item = 0 WHERE ID_advertisement = ? RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(STATEMENT_EDIT);
            pstmt.setInt(1, idAdvertisement);

            rs = pstmt.executeQuery();

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
    }

    /**
     * Searches an Advertisement by ID_ADVERTISEMENT
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static Advertisement searchAdvertisement(int reqIdAdvertisement) throws SQLException, NamingException {

        final String STATEMENT =
                "SELECT * " +
                        "FROM ADVERTISEMENT " +
                        "WHERE ID_ADVERTISEMENT = ? AND NUM_TOT_ITEM > 0;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
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
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return advertisement;
    }



    /**
     * Searches an Advertisement by company email
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Advertisement> searchAdvertisement(String reqEmail) throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT *\n" +
                        "FROM ADVERTISEMENT\n" +
                        "WHERE email_c = ? AND NUM_TOT_ITEM > 0;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Advertisement adv=null;

        // the results of the search
        List<Advertisement> listAdvertisement=new ArrayList<Advertisement>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reqEmail);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                adv = new Advertisement(
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

                listAdvertisement.add(adv);
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return listAdvertisement;
    }


    /**
     * Searches and returns some Advertisements by ID_CITY, ID_TYPE and DATE_START
     *
     * @throws SQLException
     *             if any error occurs.
     * @throws NamingException
     *             if any error occurs.
     */
    public static List<Advertisement> searchAdvertisement(final int reqIdCity, final int reqIdType,
                                                   final Date reqDate) throws SQLException, NamingException {
        final String STATEMENT =
                "SELECT * " +
                        "FROM ADVERTISEMENT " +
                        "JOIN COMPANY ON COMPANY.EMAIL_C = ADVERTISEMENT.EMAIL_C " +
                        "JOIN CITY ON CITY.ID_CITY = COMPANY.ID_CITY " +
                        "JOIN TYPE_ADVERTISEMENT ON TYPE_ADVERTISEMENT.ID_Type = ADVERTISEMENT.ID_Type " +
                        "WHERE advertisement.num_tot_item > 0 AND CITY.id_city = ? AND " +
                        "TYPE_ADVERTISEMENT.id_type = ? AND " +
                        "DATE_START >= ?;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Advertisement> advertisements = new ArrayList<Advertisement>();

        Advertisement adv=null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, reqIdCity);
            pstmt.setInt(2, reqIdType);
            pstmt.setDate(3, reqDate);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                adv = new Advertisement(
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

                advertisements.add(adv);

            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }

        return advertisements;
    }
}
