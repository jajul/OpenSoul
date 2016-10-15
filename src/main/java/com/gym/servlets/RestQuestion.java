package com.gym.servlets;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuestionStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
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
        logger.info("Get question");

        try {
            Integer num = (request.getParameter("num") != null ? Integer.parseInt(request.getParameter("num")) : 1);
            Question question = QuestionStore.getQuestion(num+1);
            if(question != null){
                out.write(question.toJson().toString());
            }
            else{
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        catch(Exception ex){
            logger.error(ex);
        }
    }
}