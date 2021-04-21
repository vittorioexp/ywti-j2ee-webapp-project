package it.unipd.dei.yourwaytoitaly.utils;

import it.unipd.dei.yourwaytoitaly.resource.Message;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.naming.Context;
import javax.naming.InitialContext;
/**
 * Class managing Registration Email confirmation
 * @author Francesco Giurisato
 */

public class EmailSender {

    /** Email destination address
     * */
    private final String email_to;
    /** Email account sending site email
     * */
    private final String emailSite;
    /** Email account password
     * */
    private final String passEmail;

    public EmailSender (String email , String emailSite , String passEmail){
        this.email_to = email;
        this.emailSite = emailSite;
        this.passEmail = passEmail;

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

            //using Google SMTP server to send emails

            MultiPartEmail mail = new MultiPartEmail();
            mail.setSSLOnConnect(true);
            mail.setSmtpPort(465);
            mail.setHostName("smtp.gmail.com");
            mail.addTo(email_to);
            mail.setFrom(emailSite);
            mail.setAuthentication(emailSite, passEmail);
            mail.setSubject(subject);
            mail.setMsg(message);
            mail.send();
            return true;
        }catch(Exception ex){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            Message m = new Message("Failed to send registration email, check the email syntax.",
                    ec.getErrorCode(), ex.toString());
            System.out.println(m.getMessage() + " " + m.getErrorDetails());
            return false;
        }
    }
}
