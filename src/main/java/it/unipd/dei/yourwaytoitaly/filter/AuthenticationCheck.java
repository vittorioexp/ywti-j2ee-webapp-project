package it.unipd.dei.yourwaytoitaly.filter;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.servlet.LoginServlet;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig == null) {
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = filterConfig;
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
            if ( LoginServlet.checkSessionEmail(req, "") ) {

                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message("User not found.",
                        ec.getErrorCode(), "");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;

//                session.invalidate();
//                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
//                return;
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