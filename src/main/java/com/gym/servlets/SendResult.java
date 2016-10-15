package com.gym.servlets;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuestionStore;
import com.gym.utils.mail.SendMail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Gochan on 15.10.2016.
 */
@WebServlet("/send_result")
public class SendResult extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(SendResult.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            SendMail sendMail = new SendMail();
            sendMail.generateAndSendEmail(request.getParameter("user"), request.getParameter("email"), request.getParameter("URL").replace("{\"url\":\"", "").replace("\"}", ""));
        } catch (MessagingException e) {
            logger.error("Can't send email", e);
        }
    }
}