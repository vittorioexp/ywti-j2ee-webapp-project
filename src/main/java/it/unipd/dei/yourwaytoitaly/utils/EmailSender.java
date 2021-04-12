package it.unipd.dei.yourwaytoitaly.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EmailSender {

    private final String email_to;


    public EmailSender (String email){
        email_to = email;
    }


    /**
     * Manages Registration Email confirmation
     * @param  subject
     *            subject of email.
     * @param message
     *            email message
     * @return true if email is sent, false if not
     */

    public boolean sendConfirmationEmail(String subject, String message){

        try {
            String email_from = "yourwaytoitalywebapp@gmail.com";
            String email_password = "ywtiwa2021";
            MultiPartEmail mail = new MultiPartEmail();
            mail.setHostName("smtp.gmail.com");
            mail.addTo(email_to);
            mail.setFrom(email_from);
            mail.setAuthentication(email_from, email_password);
            mail.setSubject(subject);
            mail.setMsg(message);
            mail.send();
            return true;
        }catch(EmailException e){
            return false;
        }
    }
}
