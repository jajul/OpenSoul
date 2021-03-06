package com.gym.utils.mail;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuizQuetion;
import com.gym.user.User;
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

    public void generateAndSendEmail(User user, String link) throws AddressException, MessagingException {
        logger.debug("UserName = {}", user.getUserName());
        logger.debug("UserEmail = {}", user.getUserEmail());
        logger.debug("Link = {}", link);
        // Step1
        logger.info("\n 1st ===> setup Mail Server Properties..");
        Properties properties = new Properties();
        try (InputStream fis = (InputStream) SendMail.class.getClassLoader().getResourceAsStream("mail.properties")) {
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
                .append(user.getUserName())
                .append(": <a href=\"" + link + "\">" + link + "</a>")
                .append("<br><hr>")
                .append("<br>Candidate' test results: ")
                .append(user.getQuizResult().getTestResult()*100)
                .append("%<hr>");

        for (Question question : user.getQuizResult().getQuizResults().keySet()) {
            QuizQuetion q = (QuizQuetion) question;
            emailBody.append("<br>Question: ").append(q.getText()).append("<ol>");
            q.getOptions().stream().forEach(x ->  emailBody.append("<li>").append(x.getText()).append("</li>"));
            emailBody.append("</ol>Candidate' answer: ").append(user.getQuizResult().getQuizResults().get(question));
            emailBody.append("<br>Right answer: ").append(q.getAnswer_num()).append("<br><hr>");
        }

        emailBody.append("<br>Email for contact with candidate: <a href=\"mailto:" + user.getUserName() + ">" + user.getUserEmail() + "</a>")
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
