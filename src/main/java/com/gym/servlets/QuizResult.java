package com.gym.servlets;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuestionStore;
import com.gym.user.User;
import com.gym.user.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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
@WebServlet("/send_quiz_result")
public class QuizResult extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(QuizResult.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String login = request.getParameter("login");
            String userName = request.getParameter("user");
            String email = request.getParameter("email");
            String type = request.getParameter("type");
            Integer num = (request.getParameter("num") != null ? Integer.parseInt(request.getParameter("num")) : 1);
            Integer answer = (request.getParameter("answer") != null ? Integer.parseInt(request.getParameter("num")) : 0);

            Question question = QuestionStore.getQuestionByUser(login, type, num);

            User user = UserInfo.getUser(login, userName, email);
            user.getQuizResult().addResult(question, answer);

        }
        catch(Exception ex){
            logger.error(ex);
            out.write(new JSONObject(ex).toString());
        }
    }
}