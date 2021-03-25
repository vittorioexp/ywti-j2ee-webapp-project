package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class for inserting a User (Tourist or Company) inside the database
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateUserDatabase {

    private static final String STATEMENT_USER = "INSERT INTO YWTIDB.User " +
            "(ID_user, email, phone_number, address, password, user_name, ID_city) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String STATEMENT_TOURIST = "INSERT INTO YWTIDB.Tourist " +
            "(ID_user, surname, name, birth_date) VALUES (?, ?, ?, ?)";

    private static final String STATEMENT_COMPANY = "INSERT INTO YWTIDB.Company " +
            "(ID_user, denomination) VALUES (?, ?)";


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
    public void createUser() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT_USER);
            pstmt.setInt(1, user.getIdUser());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhoneNumber());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getUserName());
            pstmt.setInt(7, user.getIdCity());
            pstmt.execute();

            if (this.user instanceof Tourist) {
                pstmt = con.prepareStatement(STATEMENT_TOURIST);
                pstmt.setInt(1, user.getIdUser());
                pstmt.setString(2, user.getSurname());
                pstmt.setString(3, user.getName());
                pstmt.setDate(4, user.getBirthDate());
                pstmt.execute();
            } else if (this.user instanceof Company) {
                pstmt = con.prepareStatement(STATEMENT_COMPANY);
                pstmt.setInt(1, user.getIdUser());
                pstmt.setString(2, user.getDenomination());
                pstmt.execute();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}


