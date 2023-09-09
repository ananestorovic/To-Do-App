package aspects.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSender {
    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());
    private final String username;
    private final String password;
    private final Properties properties;

    public EmailSender(String host, int port, String username, String password) {
        properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
        this.username = username;
        this.password = password;
    }

    private static MimeBodyPart getMimeBodyPart(String msg) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        return mimeBodyPart;
    }

    public void sendEmail(String toEmail, String subject, String msg) {
        Session session = getSession(username);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();

            MimeBodyPart mimeBodyPart = getMimeBodyPart(msg);
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("INFO: Email sent successfully.");
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Error sending email:{0}", e.getMessage());
        }

    }

    private Session getSession(String fromEmail) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
    }
}