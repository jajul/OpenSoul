package com.gym.servlets;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuestionStore;
import com.gym.user.User;
import com.gym.user.UserInfo;
import com.gym.user.UserQuizResult;
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
@WebServlet("/get_quiz_result")
public class GetQuizResult extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetQuizResult.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String login = request.getParameter("login");
            logger.debug(String.format("request: get_quiz_result[login='%s']", login));

            User user = UserInfo.getUser(login);
            logger.debug(String.format("Get quiz result for user: %s", user.getUserName()));

            UserQuizResult quizResult = user.getQuizResult();
            Double result = quizResult.getTestResult();
            logger.debug(String.format("Result of %s is %f", user.getUserName(), result));

            out.write(new JSONObject().put("result", result).toString());
        }
        catch(Exception ex){
            logger.error(ex);
            out.write(new JSONObject(ex).toString());
        }
    }
}