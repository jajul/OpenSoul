package com.gym.servlets;

import com.gym.logic.question.PlainQuestion;
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
@WebServlet("/get_question")
public class RestQuestion extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RestQuestion.class);

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
            logger.info(String.format("request: get_question[user='%s', type='%s', num=%d]", login, type, num));

            User user = UserInfo.getUser(login, userName, email);

            Question question = QuestionStore.getQuestionByUser(login, type, num);

            if(question != null){
//                user.getQuizResult().addQuestion(question);
                String resp = question.toJSON().toString();
                logger.debug("response: " + resp);
                out.write(resp);
            }
            else{
                JSONObject resp = new JSONObject();
                resp.put("type", type);
                resp.put("num", -1);
                logger.debug("response: " + resp);
                out.write(resp.toString());
            }
        }
        catch(Exception ex){
            logger.error(ex);
            out.write(new JSONObject(ex).toString());
        }
    }
}

