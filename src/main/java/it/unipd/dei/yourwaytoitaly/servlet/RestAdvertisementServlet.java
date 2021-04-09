package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.rest.AdvertisementRestResource;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public final class RestAdvertisementServlet extends AbstractDatabaseServlet {
    // .../show-advertisement/{idAdvertisement}
    // .../show-advertisement-list/
    // .../show-user
    // .../show-bookings
    // .../login , register

    // servlet > RestAdvertisementServlet.java GET PUT
    // rest > AdvertisementRestResource.java

    /**
     * The any MIME media type
     */
    private static final String ALL_MEDIA_TYPE = "*/*";

    @Override
    protected final void service(final HttpServletRequest req, final HttpServletResponse res)
            throws ServletException, IOException {

        //res.setContentType(JSON_UTF_8_MEDIA_TYPE);
        final OutputStream out = res.getOutputStream();

        try {
            // if the request method and/or the MIME media type are not allowed, return.
            // Appropriate error message sent by {@code checkMethodMediaType}
            if (!checkMethodMediaType(req, res)) {
                return;
            }

            // if the requested resource was an Advertisement, delegate its processing and return
            if (processAdvertisement(req, res)) {
                return;
            }

            // if none of the above process methods succeeds, it means an unknown resource has been requested
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            final Message m = new Message("Unknown resource requested.",
                    ec.getErrorCode(),String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        } finally {
            // ensure to always flush and close the output stream
            out.flush();
            out.close();
        }
    }

    /**
     * Checks that the request method and MIME media type are allowed.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res)
            throws IOException, ServletException {

        final String method = req.getMethod();
        final String contentType = req.getHeader("Content-Type");
        final String accept = req.getHeader("Accept");
        final OutputStream out = res.getOutputStream();

        Message m = null;

        if(accept == null) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            m = new Message("Unknown resource requested.",
                    ec.getErrorCode(),String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            return false;
        }

        if(!accept.equals(ALL_MEDIA_TYPE)) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            m = new Message("Unknown resource requested.",
                    ec.getErrorCode(),String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            return false;
        }

        switch(method) {
            case "GET":
            case "PUT":
            case "POST":
                return true;
            case "DELETE":
                return false;
            default:
                ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                m = new Message("Unknown resource requested.",
                        ec.getErrorCode(),String.format("Requested operation is %s.", method));
                res.setStatus(ec.getHTTPCode());
                req.setAttribute("message", m);
                return false;
        }
    }


    /**
     * Checks whether the request is for an Advertisement resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for an {@code Advertisement}; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processAdvertisement(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        final String method = req.getMethod();
        final OutputStream out = res.getOutputStream();

        String path = req.getRequestURI();
        Message m = null;

        try {
            if (path.length() == 0 || path.equals("/")) {

                switch (method) {
                    case "GET":
                        if (path.contains("list")) {
                            new AdvertisementRestResource(req, res, getDataSource().getConnection()).listAdvertisement();
                        } else if (path.contains("advertisement")) {
                            new AdvertisementRestResource(req, res, getDataSource().getConnection()).showAdvertisement();
                        }
                        break;
                    case "POST":
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).insertAdvertisement();
                        break;
                    case "PUT":
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).editAdvertisement();
                        break;
                    default:
                        ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                        m = new Message("Unknown resource requested.",
                                ec.getErrorCode(),String.format("Requested operation is %s.", method));
                        res.setStatus(ec.getHTTPCode());
                        req.setAttribute("message", m);
                        req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
                        break;
                }
            }
        } catch(Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            m = new Message("Generic error",
                    ec.getErrorCode(),"Cannot create the advertisement.");
            res.setStatus(ec.getHTTPCode());
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/show-message.jsp").forward(req, res);
        }
        return true;
    }
}
