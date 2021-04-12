package it.unipd.dei.yourwaytoitaly.filter;

import it.unipd.dei.yourwaytoitaly.database.UserDAO;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.resource.User;
import it.unipd.dei.yourwaytoitaly.servlet.AbstractDatabaseServlet;
import it.unipd.dei.yourwaytoitaly.servlet.LoginServlet;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Servlet class, to be written
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class AuthenticationCheck implements Filter {

    /*
    * Base 64 Decoder
    * */
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    /*
     * The name of the user attribute in the session
     * */
    private static final String USER_ATTRIBUTE = "user";
    /*
     * The configuration for the filter
     * */
    private FilterConfig config = null;
    /*
     * The connection to the database
     * */
    private DataSource ds;

//    public AuthenticationCheck (HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
//        this.session = req.getSession(false);
//        if (session == null || session.getAttribute("email") == null) {
//            session.invalidate();
//            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
//        }
//    }
//
//    public String getEmail(){ return session.getAttribute("email").toString();}
//    public String getPassword() {return  session.getAttribute("password").toString();}
//    public User getUser() throws SQLException, NamingException { return UserDAO.searchUserLogin(getEmail(), getPassword());    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig == null) {
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = filterConfig;
//
        //TODO : understand whether I have to check the db pool connection
//        InitialContext cxt;
//        try{
//            cxt = new InitialContext();
//            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/YWTI");
//        }catch (NamingException e){
//            ds = null;
//            throw new ServletException("Impossible to connect to to database pool");
//        }

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)){
            throw new ServletException("Only HTTP requests or response are accepted");
        }

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession(false);

        if (session == null ){
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            return;
        }
        else{
            if (session.getAttribute("Authentication") == null || LoginServlet.checkSessionEmail(req, "") ) {
                session.invalidate();
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
                return;
            }

        }

        filterChain.doFilter( req , res );

    }

    @Override
    public void destroy() {
        config = null;
        ds = null;
    }

}