package it.unipd.dei.yourwaytoitaly.database;


import it.unipd.dei.yourwaytoitaly.resource.TypeAdvertisement;

import java.sql.Connection;
import java.sql.SQLException;

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

public class SearchTypeAdvertisement {

    /**
     * The SQL statements to be executed
     */
    // TODO: write query to search for ID or for name
    private static final String STATEMENT_ID = "";

    private static final String STATEMENT_NAME = "";

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
     * Creates a new object for searching type advertisement by idType
     *
     * @param con       the connection to the database.
     * @param idType    the Id type of the advertisement.
     */
    public SearchTypeAdvertisement(final Connection con, final int idType) {
        this.con = con;
        this.idType = idType;
        this.type = null;
    }

    /**
     * Creates a new object for searching type advertisement by type
     *
     * @param con   the connection to the database.
     * @param type  the type of the advertisement.
     */
    public SearchTypeAdvertisement(final Connection con, final String type) {
        this.con = con;
        this.type = type;
        this.idType = -1;
    }

    /**
     * Searches bookings by tourist.
     *
     * @return a list of {@code Booking} objects matching with the Id of the advertisement.
     * @throws SQLException if any error occurs while searching for bookings.
     */

    public TypeAdvertisement search() throws SQLException {
        //TODO: write function body
        //if type != -1, search type adv based on id and return TypeAdvertisement
        //else search type adv based on the name and return TypeAdvertisement
        return null;
    }

}
