package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.rest.AdvertisementRestResource;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
GET /adv		->	RestAdvertisementServlet.java > AdvertisementRestResource.java > listAdvertisements() > JSON list

GET /adv-show/ID	->	show-advertisement.jsp

GET /adv/ID		->	RestAdvertisementServlet.java > AdvertisementRestResource.java > getAdvertisement() > JSON obj

PUT /adv/ID		->	RestAdvertisementServlet.java > AdvertisementRestResource.java > editAdvertisement() > redirect to /adv-show/ID

GET /adv/ID/image	->	RestAdvertisementServlet.java > AdvertisementRestResource.java > listImages() > JSON list

GET /adv/ID/feedback	->	RestAdvertisementServlet.java > AdvertisementRestResource.java > listFeedback() > JSON list

GET /adv/ID/booking	->	RestAdvertisementServlet.java > AdvertisementRestResource.java > listBookings() > JSON list

POST /adv-create	->	RestAdvertisementServlet.java > AdvertisementRestResource.java > insertAdvertisement() > redirect to /upload-images/ID

DELETE /adv/ID		->	RestAdvertisementServlet.java > AdvertisementRestResource.java > deleteAdvertisement() > redirect to /user/profile



mapping

toJson / fromJson : images, feedback, booking

RestAdvertisementServlet.java

AdvertisementRestResource.java
 */

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
            if (processRequest(req, res)) {
                return;
            }

            // if none of the above process methods succeeds, it means an unknown resource has been requested
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            final Message m = new Message("Unknown resource requested.",
                    ec.getErrorCode(),req.getMethod() + " at " + req.getRequestURI() + " not supported");
            res.setStatus(ec.getHTTPCode());
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m.toJSON(res.getOutputStream());
            return;

        } catch(Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Internal error while processing REST requests",
                    ec.getErrorCode(),ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
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
        // TODO: fix contentType
        /*
        if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            ErrorCode ec = ErrorCode.OPERATION_UNKNOWN;
            m = new Message("Unsupported output media type. Resources are represented only in application/json.",
                    ec.getErrorCode(), String.format("Requested representation is %s.", accept));
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return false;
        }*/

        switch(method) {
            case "GET":
            case "PUT":
            case "POST":
            case "DELETE":
                return true;
            default:
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
    private boolean processRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

        final String method = req.getMethod();

        String URI = req.getRequestURI();
        Message m = null;

        // the requested resource was not an advertisement
        if (URI.lastIndexOf("/adv") <= 0) {
            return false;
        }

        String path = URI.substring(URI.lastIndexOf("adv"));

        if (path.equals("adv-create")) {
            switch (method) {
                case "POST":    // POST /adv-create
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).insertAdvertisement();
                    break;
                default:
                    return false;
            }
        } else if (path.contains("adv/")) {
            if (path.contains("/image")) {
                switch (method) {
                    case "GET":     // GET /adv/ID/image
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).listImages();
                        break;
                    default:
                        return false;
                }
            } else if (path.contains("/feedback")) {
                switch (method) {
                    case "GET":     // GET /adv/ID/feedback
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).listFeedback();
                        break;
                    default:
                        return false;
                }
            } else if (path.contains("/booking")) {
                switch (method) {
                    case "GET":     // GET /adv/ID/booking
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).listBookings();
                        break;
                    default:
                        return false;
                }
            } else if (path.contains("/rate")) {
                switch (method) {
                    case "GET":     // GET /adv/ID/rate
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).getRate();
                        break;
                    default:
                        return false;
                }
            } else {
                switch (method) {
                    case "GET":     // GET /adv/ID
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).getAdvertisement();
                        break;
                    case "PUT":    // PUT /adv/ID
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).editAdvertisement();
                        break;
                    case "DELETE":  // DELETE /adv/ID
                        new AdvertisementRestResource(req, res, getDataSource().getConnection()).deleteAdvertisement();
                        break;
                    default:
                        return false;
                }
            }
        } else if (path.equals("adv")) {
            switch (method) {
                case "GET":     // GET /adv
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).listAdvertisements();
                    break;
                default:
                    return false;
            }

        } else {
            return false;
        }




        /*
        String tempPath = path.substring(path.lastIndexOf("list") + 5);

        // the requested URI is /list/advertisement
        // if method GET, list advertisements by search criteria
        if (tempPath.equals("advertisement")) {
            switch (method) {
                case "GET":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).listAdvertisements();
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
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).getAdvertisement();
                    break;
                case "POST":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).insertAdvertisement();
                    break;
                case "PUT":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).editAdvertisement();
                    break;
                case "DELETE":
                    new AdvertisementRestResource(req, res, getDataSource().getConnection()).deleteAdvertisement();
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
        */
        return true;
    }
}
