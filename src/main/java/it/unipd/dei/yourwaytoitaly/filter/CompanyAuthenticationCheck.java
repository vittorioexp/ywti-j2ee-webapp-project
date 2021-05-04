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
 * Filter checking the user has a company role to access the resource
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class CompanyAuthenticationCheck implements Filter {

    /*
     * Base 64 Decoder
     * */
    private static final Base64.Decoder DECODER = Base64.getDecoder();
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

    /** Method filtering pages that requires an active session and a company role to be reached
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     * */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession(false);
        //Checking session presence end eventually redirecting to login page
        if (session == null ){
            req.getRequestDispatcher("/user/do-login").forward(req, res);
            return;
        }
        else{
            //checking session email is not empty
            if (LoginServlet.checkSessionEmail(req, "") ) {
                session.invalidate();
                req.getRequestDispatcher("/user/do-login").forward(req, res);
                return;
            }
            try {
                //checking that session email is referred to a company account
                String role = LoginServlet.getUserRole(req);
                if (!role.equals("company")) {
                    ErrorCode ec = ErrorCode.USER_NOT_ALLOWED;
                    Message m = new Message(ec.getErrorMessage(),
                            ec.getHTTPCode(),"You need a company account to access this page");
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    return;
                }

            }catch(Exception e){
                session.invalidate();
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"Internal error has occurred");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
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