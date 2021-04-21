package it.unipd.dei.yourwaytoitaly.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * Represents a generic REST resource.
 *
 * @author Vittorio Esposito
 * @version 1.00
 * @since 1.00
 */
public abstract class RestResource {

    /**
     * The HTTP request
     */
    protected final HttpServletRequest req;

    /**
     * The HTTP response
     */
    protected final HttpServletResponse res;

    /**
     * The connection to the database
     */
    protected final Connection con;

    /**
     * Creates a new REST resource.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    protected RestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        this.req = req;
        this.res = res;
        this.con = con;
    }
}