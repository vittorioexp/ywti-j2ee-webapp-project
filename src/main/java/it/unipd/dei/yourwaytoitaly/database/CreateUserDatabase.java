package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for inserting a User (Tourist or Company) inside the database
 * @author Vittorio Esposito
 * @author Marco Basso
 * @author Matteo Piva
 * @author Alessandro Benetti
 * @version 1.0
 * @since 1.0
 */


public final class CreateUserDatabase {

    private static final String STATEMENT_TOURIST = "INSERT INTO YWTIDB.Tourist (email_t, surname, name, birth_date, phone_number, address, password, ID_city)" +
                                                    "SELECT ?, ?, ?, ?, ?, ?, ?, ID_CITY" +
                                                    "FROM City WHERE City.name = ? RETURNING *;";

    private static final String STATEMENT_COMPANY = "INSERT INTO YWTIDB.Company (email_c, name_c, phone_number, address, password, ID_city)" +
                                                    "SELECT ?, ?, ?, ?, ?, ID_CITY" +
                                                    "FROM City WHERE City.name = ? RETURNING *;";

    private final Connection con;
    private final User user;

    public CreateUserDatabase(final Connection con, final Tourist tourist) {
        this.con = con;
        this.user = tourist;
    }
    public CreateUserDatabase(final Connection con, final Company company) {
        this.con = con;
        this.user = company;
    }
    public User createUser() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User u = null;
        
        try {
            if (this.user instanceof Tourist) {
                pstmt = con.prepareStatement(STATEMENT_TOURIST);
                pstmt.setString(1, ((Tourist) this.user).getEmail());
                pstmt.setString(2, ((Tourist) this.user).getSurname());
                pstmt.setString(3, ((Tourist) this.user).getName());
                pstmt.setDate(4, ((Tourist) this.user).getBirthDate());
                pstmt.setString(5, ((Tourist) this.user).getPhoneNumber());
                pstmt.setString(6, ((Tourist) this.user).getAddress());
                pstmt.setString(7, ((Tourist) this.user).getPassword());

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
            } else if (this.user instanceof Company) {
                pstmt = con.prepareStatement(STATEMENT_COMPANY);
                pstmt.setString(1, ((Company) this.user).getEmail());
                pstmt.setString(2, ((Company) this.user).getName());
                pstmt.setString(3, ((Company) this.user).getPhoneNumber());
                pstmt.setString(4, ((Company) this.user).getAddress());
                pstmt.setString(5, ((Company) this.user).getPassword());
                pstmt.setInt(6, ((Company) this.user).getIdCity());
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
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
        return u;
    }
}

