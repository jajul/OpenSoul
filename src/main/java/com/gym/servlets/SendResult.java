package com.gym.servlets;

import com.gym.user.User;
import com.gym.user.UserInfo;
import com.gym.utils.mail.SendMail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            String login = request.getParameter("login");
            String userName = request.getParameter("user");
            String email = request.getParameter("email");

            User user = UserInfo.getUser(login, userName, email);

            try {
                SendMail sendMail = new SendMail();
                String url = "";
                try {
                    url = request.getParameter("URL").replace("{\"url\":\"", "").replace("\"}", "");
                }catch (Exception e){
                    logger.error(e);
                }
                sendMail.generateAndSendEmail(user, url);
            } catch (Exception e) {
                logger.error("Can't send email", e);
            }
        } catch (Exception e) {

        }
    }
}