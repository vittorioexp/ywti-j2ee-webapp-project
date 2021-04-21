package it.unipd.dei.yourwaytoitaly.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Class to provide the data source
 * @author Francesco Giurisato
 * @version 1.0
 * @since 1.0
 */
public class DataSourceProvider {

    //static datasource
    private static DataSource ds = null;

    /**
     * Gets the data source
     *
     * @throws NamingException if any error occurs
     */
    public static synchronized DataSource getDataSource() throws NamingException {

        // we don't want to initialize a new datasource everytime, so, we check first that ds is null
        if (ds == null) {

            //we get the context
            InitialContext ctx = new InitialContext();

            //and use the proper resource to initialize the datasource
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/YWTI");
        }
        return ds;
    }
}