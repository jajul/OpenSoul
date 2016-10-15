package com.gym.utils.mail;

import com.gym.servlets.StartServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Julia on 15.10.2016.
 */
public class SendMail {
    private static final Logger logger = LogManager.getLogger(SendMail.class);

    public static void generateAndSendEmail(String user, String email, String link) throws AddressException, MessagingException {
        // Step1
        logger.info("\n 1st ===> setup Mail Server Properties..");
        Properties properties = new Properties();
        try (InputStream fis = (InputStream) StartServlet.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            logger.error("Can't find email properties");
        }
        logger.info("Mail Server Properties have been setup successfully..");

        // Step2
        logger.info("\n\n 2nd ===> get Mail Session..");
        Session getMailSession = Session.getDefaultInstance(properties, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);

        for (String recipient : properties.getProperty("mail.list").split(";")) {
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }

        for (String recipient : properties.getProperty("mail.cc.list").split(";")) {
            generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
        }
        generateMailMessage.setSubject("Greetings from OpenSoul project!");
        StringBuilder emailBody = new StringBuilder("Video from candidate ")
                .append(user)
                .append(": <a href=\"" + link + "\">" + link + "</a>")
                .append("<br>Email for contact with candidate: <a href=\"mailto:" + email + ">" + email + "</a>")
                .append("<br><br>Regards, <br> OpenSoul Admin");
        generateMailMessage.setContent(emailBody.toString(), "text/html");
        logger.info("Mail Session has been created successfully..");

        // Step3
        logger.info("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password

        transport.connect(properties.getProperty("mail.smtp.server"), properties.getProperty("mail.address"), properties.getProperty("mail.password"));
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
