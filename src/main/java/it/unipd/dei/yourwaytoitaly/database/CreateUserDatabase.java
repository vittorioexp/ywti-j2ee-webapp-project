package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.*;

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
    public User createUser() throws SQLException {
        PreparedStatement pstmt = null;
        User u = null;
        try {
            if (this.user instanceof Tourist) {
                /*pstmt = con.prepareStatement(STATEMENT_TOURIST);
                pstmt.setInt(1, ((Tourist) user).getIdUser());
                pstmt.setString(2, ((Tourist) user).getSurname());
                pstmt.setString(3, ((Tourist) user).getName());
                pstmt.setDate(4, ((Tourist) user).getBirthDate());
                pstmt.execute();*/
            } else if (this.user instanceof Company) {
                /*pstmt = con.prepareStatement(STATEMENT_COMPANY);
                pstmt.setInt(1, ((Company) user).getIdUser());
                pstmt.setString(2, ((Company) user).getDenomination());
                pstmt.execute();*/
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
        return u;
    }
}


