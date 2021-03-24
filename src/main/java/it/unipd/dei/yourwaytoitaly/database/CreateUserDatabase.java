package it.unipd.dei.yourwaytoitaly.database;

import it.unipd.dei.yourwaytoitaly.resource.DatabaseEntities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class for accessing the database, copies to/from application data structures,
 * ensures to release the connection, and delegates the management of any exception to the caller class
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */


public final class CreateEmployeeDatabase {

    private static final String STATEMENT = "INSERT INTO YWTIDB.User " +
            "(badge, surname, age, salary) VALUES (?, ?, ?, ?)";
    private final Connection con;
    private final Employee employee;
    public CreateEmployeeDatabase(final Connection con, final Employee employee) {
        this.con = con;
        this.employee = employee;
    }
    public void createEmployee() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, employee.getBadge());
            pstmt.setString(2, employee.getSurname());
            pstmt.setInt(3, employee.getAge());
            pstmt.setInt(4, employee.getSalary());
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
    }
}


