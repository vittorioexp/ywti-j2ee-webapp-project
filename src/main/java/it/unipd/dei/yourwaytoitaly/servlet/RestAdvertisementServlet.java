package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.rest.AdvertisementRestResource;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class RestAdvertisementServlet extends AbstractDatabaseServlet {

    /**
     * The JSON MIME media type
     */
    private static final String JSON_MEDIA_TYPE = "application/json";

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * The any MIME media type
     */
    private static final String ALL_MEDIA_TYPE = "*/*";

    @Override
    protected final void service(final HttpServletRequest req, final HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

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
                    ec.getErrorCode(),String.format("A.Requested resource is %s.", req.getRequestURI()));
            res.setStatus(ec.getHTTPCode());
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m.toJSON(res.getOutputStream());

        } catch(Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Generic error",
                    ec.getErrorCode(),ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
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
            throws IOException {

        final String method = req.getMethod();
        final String contentType = req.getHeader("Content-Type");
        final String accept = req.getHeader("Accept");

        Message m = null;

        if(accept == null) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            m = new Message("Unknown resource requested.",
                    ec.getErrorCode(),String.format("B.Requested resource is %s.", req.getRequestURI()));
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
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
                m.toJSON(res.getOutputStream());
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
    private boolean processAdvertisement(HttpServletRequest req, HttpServletResponse res) throws Exception {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        // the requested resource was not an advertisement
        if (path.lastIndexOf("advertisement") <= 0) {
            return false;
        }

        String tempPath = path.substring(path.lastIndexOf("list") + 5);

        // the requested URI is /list/advertisement
        // if method GET, list advertisements by search criteria
        if (tempPath.equals("advertisement")) {
            switch (method) {
                case "GET":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).listAdvertisement();
                    break;
                default:
                    ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
                    m = new Message("Unknown resource requested.",
                            ec.getErrorCode(), String.format("Requested operation is %s.", method));
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }

        // the request URI is: /advertisement/{idAdvertisement}
        // if method GET,  show the advertisement
        // if method POST, create the advertisement
        // if method PUT,  edit the advertisement
        else {

            switch (method) {
                case "GET":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).showAdvertisement();
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
                            ec.getErrorCode(), String.format("Requested operation is %s.", method));
                    res.setStatus(ec.getHTTPCode());
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }
        return true;
    }
}
