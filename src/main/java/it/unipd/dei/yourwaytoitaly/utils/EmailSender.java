package it.unipd.dei.yourwaytoitaly.utils;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Class to send the confirmation email to the user
 * @author Francesco Giurisato
 * @version 1.0
 * @since 1.0
 */
public class EmailSender {

    /**
     * Email destination address
     */
    private final String email_to;

    /**
     * Class constructor
     *
     * @param email
     *          email address of the user
     *
     */
    public EmailSender (String email){
        email_to = email;
    }


    /**
     * Manages Registration Email confirmation
     * @param subject
     *            subject of email.
     * @param message
     *            email message
     * @return true if email is sent, false if not
     */
    public boolean sendConfirmationEmail(String subject, String message){

        try {
            //using Google SMTP server to send emails
            String email_from = "yourwaytoitalywebapp@gmail.com";
            String email_password = "waywti2021";
            MultiPartEmail mail = new MultiPartEmail();
            mail.setSSLOnConnect(true);
            mail.setSmtpPort(465);
            mail.setHostName("smtp.gmail.com");
            mail.addTo(email_to);
            mail.setFrom(email_from);
            mail.setAuthentication(email_from, email_password);
            mail.setSubject(subject);
            mail.setMsg(message);
            mail.send();
            return true;
        }catch(EmailException ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Failed to send registration email, check the email syntax.",
                    ec.getErrorCode(), ex.toString());
            System.out.println(ex.getMessage());
            System.out.println(email_to);
            return false;
        }
    }
}
