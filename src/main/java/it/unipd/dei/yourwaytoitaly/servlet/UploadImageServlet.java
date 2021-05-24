package it.unipd.dei.yourwaytoitaly.servlet;

import it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO;
import it.unipd.dei.yourwaytoitaly.database.ImageDAO;
import it.unipd.dei.yourwaytoitaly.resource.Image;
import it.unipd.dei.yourwaytoitaly.resource.Message;
import it.unipd.dei.yourwaytoitaly.utils.ErrorCode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Servlet class upload images of an Advertisement
 * @author Vittorio Esposito
 * @version 1.0
 * @since 1.0
 */

public class UploadImageServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * Upload images from a multipart/form-data
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws IOException if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType(JSON_UTF_8_MEDIA_TYPE);
        try {
            // check if a session is valid

            String email = LoginServlet.getUserEmail(req);
            if (email.equals("")) {
                ErrorCode ec = ErrorCode.USER_NOT_FOUND;
                Message m = new Message(ec.getErrorMessage(),
                        ec.getHTTPCode(),"User not found.");
                res.setStatus(ec.getHTTPCode());
                m.toJSON(res.getOutputStream());
                return;
            }

            int idAdvertisement = 0;
            String description = "";

            // check if the servlet needs to receive images
            if (ServletFileUpload.isMultipartContent(req)) {
                String URI = req.getRequestURI();
                String pathName="";

                List<FileItem> multipart = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                int count = 1;
                for (FileItem item : multipart) {
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        switch (name) {
                            case "idAdvertisement":
                                idAdvertisement = Integer.parseInt(value);
                                if(multipart.size() == 1){
                                    res.setStatus(HttpServletResponse.SC_OK);
                                    res.sendRedirect(req.getContextPath() + "/adv-show/" + idAdvertisement);
                                }
                                if(idAdvertisement<=0){
                                    ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                                    Message m = new Message(ec.getErrorMessage(),
                                            ec.getHTTPCode(), "The requested advertisement does not exists.");
                                    res.setStatus(ec.getHTTPCode());
                                    req.setAttribute("message", m);
                                    m.toJSON(res.getOutputStream());
                                    return;
                                }
                                String emailAdv =
                                        AdvertisementDAO.searchAdvertisement(idAdvertisement).getEmailCompany();
                                if(!emailAdv.equals(email)){
                                    ErrorCode ec = ErrorCode.USER_NOT_ALLOWED;
                                    Message m = new Message(
                                            ec.getErrorMessage(),
                                            ec.getHTTPCode(), "User not allowed to upload images on this advertisement.");
                                    res.setStatus(ec.getHTTPCode());
                                    req.setAttribute("message", m);
                                    m.toJSON(res.getOutputStream());
                                    return;
                                }
                                description = AdvertisementDAO.searchAdvertisement(idAdvertisement).getTitle();
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (idAdvertisement<=0 || description.equals("")) {
                            ErrorCode ec = ErrorCode.AD_NOT_FOUND;
                            Message m = new Message(
                                    ec.getErrorMessage(),
                                    ec.getHTTPCode(), "Cannot upload images on this advertisement: " +
                                    "the requested advertisement does not exists.");
                            res.setStatus(ec.getHTTPCode());
                            m.toJSON(res.getOutputStream());
                            return;
                        }

                        // check file extension (only png and jpg allowed)
                        if (!item.getContentType().contains("image")) {
                            ErrorCode ec = ErrorCode.WRONG_FORMAT;
                            Message m = new Message(ec.getErrorMessage(),
                                    ec.getHTTPCode(), "Cannot upload this file. Use .PNG or .JPG instead");
                            res.setStatus(ec.getHTTPCode());
                            m.toJSON(res.getOutputStream());
                            return;
                        }

                        // Save the picture inside the disk
                        Date date = new Date(Calendar.getInstance().getTime().getTime());
                        Time time = new Time(Calendar.getInstance().getTime().getTime());
                        String name = (date + "_" + time + "_" + String.valueOf(count++))
                                .replace(":","")
                                .replace("-","");

                        String extension = item.getName().substring(item.getName().lastIndexOf("."));
                        pathName = System.getProperty("user.dir");
                        pathName = pathName.substring(0, pathName.lastIndexOf("bin"));
                        pathName += "webapps/ywti_wa2021_war/res/img/" +  String.valueOf(idAdvertisement) + "/";
                        pathName += name + extension;
                        try { item.write(new File(pathName)); }
                        catch (Exception e) {}

                        // Save the image URI inside the DB
                        pathName = URI.substring(0, URI.lastIndexOf("image-upload"));
                        pathName += "res/img/" + String.valueOf(idAdvertisement) + "/" + name + extension;
                        Image img = new Image
                                (
                                        0,
                                        pathName,
                                        description,
                                        idAdvertisement
                                );
                        ImageDAO.createImage(img);

                    }

                }

                res.setStatus(HttpServletResponse.SC_OK);
                res.sendRedirect(req.getContextPath() + "/adv-show/" + idAdvertisement);
            }

        } catch (Exception ex) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message(ec.getErrorMessage(),
                    ec.getHTTPCode(), "Cannot upload the image: " + ex.toString());
            res.setStatus(ec.getHTTPCode());
            m.toJSON(res.getOutputStream());
            return;
        }

    }
}
