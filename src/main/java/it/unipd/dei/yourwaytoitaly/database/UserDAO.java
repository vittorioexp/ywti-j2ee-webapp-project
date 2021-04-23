package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.*;
import it.unipd.dei.yourwaytoitaly.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO.java
 * Class for:
 * <ul>
 *  <li> inserting a User (company/tourist) </li>
 *  <li> editing a User (company/tourist) </li>
 *  <li> searching and returning a User (company/tourist) by EMAIL_T and PASSWORD </li>
 *  <li> returning the score accumulated by a User (Tourist) </li>
 * </ul>
 * inside the database
 *
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @version 1.0
 * @since 1.0
 */

public class UserDAO extends AbstractDAO{

    /**
     * Creates a new user (company/tourist).
     *
     * @return the just created user
     *
     * @throws SQLException
     *             if any error occurs while creating users.
     */
    public static User createUser(User user) throws SQLException, NamingException {
        final String STATEMENT_TOURIST =
                "INSERT INTO Tourist (email_t, surname, name, birth_date, phone_number, address, password, ID_city)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, MD5(?), ?) RETURNING *;\n";


        final String STATEMENT_COMPANY =
                "INSERT INTO Company (email_c, name_c, phone_number, address, password, ID_city)\n" +
                        "VALUES (?, ?, ?, ?, MD5(?), ?) RETURNING *;";

        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User u = null;

        try {
            if (user instanceof Tourist) {
                pstmt = con.prepareStatement(STATEMENT_TOURIST);
                pstmt.setString(1, ((Tourist) user).getEmail());
                pstmt.setString(2, ((Tourist) user).getSurname());
                pstmt.setString(3, ((Tourist) user).getName());
                pstmt.setDate(4, ((Tourist) user).getBirthDate());
                pstmt.setString(5, ((Tourist) user).getPhoneNumber());
                pstmt.setString(6, ((Tourist) user).getAddress());
                pstmt.setString(7, ((Tourist) user).getPassword());
                pstmt.setInt(8, ((Tourist) user).getIdCity());

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    u = new Tourist(
                            rs.getString("email_t"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getInt("ID_city"),
                            rs.getString("surname"),
                            rs.getDate("birth_date"));
                }
            } else if (user instanceof Company) {
                pstmt = con.prepareStatement(STATEMENT_COMPANY);
                pstmt.setString(1, ((Company) user).getEmail());
                pstmt.setString(2, ((Company) user).getName());
                pstmt.setString(3, ((Company) user).getPhoneNumber());
                pstmt.setString(4, ((Company) user).getAddress());
                pstmt.setString(5, ((Company) user).getPassword());
                pstmt.setInt(6, ((Company) user).getIdCity());
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    u = new Company(
                            rs.getString("email_c"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getInt("ID_city"),
                            rs.getString("name_c"));
                }

            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return u;
    }

    /**
     * Edits a user (company/tourist).
     *
     * @return the just created user
     *
     * @throws SQLException
     *             if any error occurs while creating users.
     */
    public static void editUserProfile(User user) throws SQLException, NamingException {
        final String STATEMENT_TOURIST_EDIT =
                "UPDATE TOURIST SET password = MD5(?) , address = ?, phone_number = ?, id_city = ? WHERE email_t = ? RETURNING *;";


        final String STATEMENT_COMPANY_EDIT =
                "UPDATE COMPANY SET password = MD5(?) , address = ?, phone_number = ?, id_city = ?  WHERE email_c = ? RETURNING *;";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (user instanceof Tourist) {
                pstmt = con.prepareStatement(STATEMENT_TOURIST_EDIT);
                pstmt.setString(1, ((Tourist) user).getPassword());
                pstmt.setString(2, ((Tourist) user).getAddress());
                pstmt.setString(3, ((Tourist) user).getPhoneNumber());
                pstmt.setInt(4, ((Tourist) user).getIdCity());
                pstmt.setString(5, ((Tourist) user).getEmail());
                rs = pstmt.executeQuery();

            } else if (user instanceof Company) {
                pstmt = con.prepareStatement(STATEMENT_COMPANY_EDIT);
                pstmt.setString(1, ((Company) user).getPassword());
                pstmt.setString(2, ((Company) user).getAddress());
                pstmt.setString(3, ((Company) user).getPhoneNumber());
                pstmt.setInt(4, ((Company) user).getIdCity());
                pstmt.setString(5, ((Company) user).getEmail());
                rs = pstmt.executeQuery();

            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return;
    }


    /**
     * Searches user by email and password
     *
     * @return a user objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs while searching.
     */
    public static User searchUserLogin(final String reqEmail, final String reqPassword) throws SQLException, NamingException {
        final String STATEMENT_T =
                "SELECT email_t, surname, name, birth_date, phone_number, address, password, ID_city " +
                        "FROM TOURIST " +
                        "WHERE email_t = ? AND password = MD5(?);";

        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        User user = null;

        try {

            pstmt = con.prepareStatement(STATEMENT_T);
            pstmt.setString(1, reqEmail);
            pstmt.setString(2, reqPassword);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = new Tourist(
                        rs.getString("email_t"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getInt("ID_city"),
                        rs.getString("surname"),
                        rs.getDate("birth_date"));
            }

            if (user == null ) {
                final String STATEMENT_C =
                        "SELECT email_c, name_c, phone_number, address, password, ID_city " +
                                "FROM COMPANY " +
                                "WHERE email_c = ? AND password = MD5(?);";

                // the results of the search

                pstmt = con.prepareStatement(STATEMENT_C);
                pstmt.setString(1, reqEmail);
                pstmt.setString(2, reqPassword);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    user = new Company(
                            rs.getString("email_c"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getInt("ID_city"),
                            rs.getString("name_c"));
                }
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }



        return user; // if search doesn't give result user=null
    }

    /**
     * Searches user by email and password
     *
     * @return a user objects matching the parameter.
     *
     * @throws SQLException
     *             if any error occurs while searching.
     */
    public static User searchUserByEmail(final String reqEmail) throws SQLException, NamingException {
        final String STATEMENT_T =
                "SELECT email_t, surname, name, birth_date, phone_number, address, password, ID_city " +
                        "FROM TOURIST " +
                        "WHERE email_t = ?;";

        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        User user = null;

        try {

            pstmt = con.prepareStatement(STATEMENT_T);
            pstmt.setString(1, reqEmail);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = new Tourist(
                        rs.getString("email_t"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getInt("ID_city"),
                        rs.getString("surname"),
                        rs.getDate("birth_date"));
            }

            if (user == null ) {
                final String STATEMENT_C =
                        "SELECT email_c, name_c, phone_number, address, password, ID_city " +
                                "FROM COMPANY " +
                                "WHERE email_c = ?;";

                // the results of the search

                pstmt = con.prepareStatement(STATEMENT_C);
                pstmt.setString(1, reqEmail);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    user = new Company(
                            rs.getString("email_c"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getInt("ID_city"),
                            rs.getString("name_c"));
                }
            }
        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }



        return user; // if search doesn't give result user=null
    }


    /**
     * Searches the user score.
     *
     * @return a TypeAdvertisement objects matching with the Id of the type advertisement.
     * @throws SQLException if any error occurs while searching for a type advertisement.
     */
    public static int getUserScore(String emailTourist) throws SQLException, NamingException {

        final String STATEMENT = "SELECT *\n" +
                "FROM BOOKING JOIN ADVERTISEMENT ON ADVERTISEMENT.ID_ADVERTISEMENT = BOOKING.ID_ADVERTISEMENT\n" +
                "WHERE BOOKING.email_t = ? AND BOOKING.state = 'SUCCESSFUL';";
        Connection con = DataSourceProvider.getDataSource().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Booking> bookings = null;
        int totalScore = 0;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, emailTourist);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                totalScore += rs.getInt("score")*rs.getInt("num_booking");
            }

        } finally {
            //close all the possible resources
            cleaningOperations(pstmt, rs, con);
        }
        return totalScore;
    }


}