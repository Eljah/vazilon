package tatar.tourism.mail;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ilya Evlampiev on 05.10.2015.
 */
public class MailUtil extends Thread {
    static Logger log = Logger.getLogger(MailUtil.class);

    String emailTo;
    String text;
    String subject;
    Properties prop = new Properties();

    public MailUtil(String emailTo, String subject, String text) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.text = text;
        InputStream input = null;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String path = new File(".").getCanonicalPath();
            log.trace("App is executed in folder " + path);
            String propertiesLocation = classLoader.getResource("emailconfig.properties").getFile();
            log.trace("Properties are located at " + propertiesLocation);
            input = new FileInputStream(new File(propertiesLocation));
            log.trace("Getting properties");
            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            log.error("Prpoperties are not fount due to " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }


    }


    public void sendEmail(String emailTo, String subject, String text) {
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("username"), prop.getProperty("password"));
                    }
                });
        log.trace("Setting up smtp session");

        try {
            log.trace("Formatting the new message");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("username")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject(subject);

            BodyPart body = new MimeBodyPart();

            /* you can add html tags in your text to decorate it. */
            body.setContent(text, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            message.setContent(multipart, "text/html; charset=UTF-8");
            log.trace("Sending message");
            Transport.send(message);
            log.trace("Message successfully sent");

        } catch (MessagingException e) {
            log.error("Message si not sent due to " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        this.sendEmail(emailTo, subject, text);
    }


}