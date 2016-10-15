package com.gym.servlets;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuestionStore;
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
@WebServlet("/send_result")
public class SendResult extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(SendResult.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            Integer num = (request.getParameter("num") != null ? Integer.parseInt(request.getParameter("num")) : 1);
            logger.info("Get question with num " + num);
            Question question = QuestionStore.getQuestion(num);
            if(question != null){
                String resp = question.toJson().toString();
                logger.debug("response: " + resp);
                out.write(resp);
            }
            else{
                JSONObject resp = new JSONObject();
                resp.put("num", -1);
                logger.debug("response: " + resp);
                out.write(resp.toString());
            }
        }
        catch(Exception ex){
            logger.error(ex);
        }
    }
}