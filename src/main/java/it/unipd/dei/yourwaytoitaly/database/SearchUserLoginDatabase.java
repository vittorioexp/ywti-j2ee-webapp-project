package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Class for searching a User inside the database and returning it as an User object:
 * receives username / email + password (reqUser / reqEmail + reqPassword) and checks if the pair is present in the DB.
 * If so, it returns the corresponding Company / Tourist object, otherwise it returns null.
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public final class SearchUserLoginDatabase {
    private static final String STATEMENT = ""; //TODO: query needed
    private final Connection con;
    private final String reqUser; // Can be modified with reqEmail
    private final String reqPassword;

    public SearchUserLoginDatabase(final Connection con, final String reqUser, final String reqPassword) {
        this.con = con;
        this.reqUser = reqUser;
        this.reqPassword = reqPassword;
    }

    public User SearchUserLogin() throws SQLException {
        //TODO: write the function body
        return null;
    }
}