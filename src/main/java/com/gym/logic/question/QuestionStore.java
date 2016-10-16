package com.gym.logic.question;

import com.gym.db.MyDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gochan on 15.10.2016.
 */
public class QuestionStore {
    private static final Logger logger = LogManager.getLogger(QuestionStore.class);

    /**
     * @param user - VOXIMPLANT user
     * @param type - quiz or plain
     * @param num - number of question
     */
    public static Question getQuestionByUser(String user, String type, Integer num) throws Exception{
        try(Connection conn = MyDataSourceFactory.getConnection()){
            Statement st = conn.createStatement();
            String sql = String.format("select THEME from VOX_USER where USER='%s'", user);
            logger.debug(String.format("executeQuery: %s", sql));
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                String theme = rs.getString("THEME");
                logger.debug(String.format("Received theme: %s", theme));
                return getQuestion(theme, type, num);
            }
            else{
                throw new SQLException(String.format("Coudn't find user '%s'", user));
            }
        }
        catch(SQLException ex){
            throw new Exception(ex);
        }
    }

    /**
     * @param theme - theme of the question
     * @param type - quiz or plain
     * @param num - number of question
     */
    public static Question getQuestion(String theme, String type, Integer num) throws Exception{
        Question question = null;
        if("quiz".equals(type)){
            return getQuizQuestion(theme, num);
//            question = getQuizQuestion(theme, num);
//            if(question == null){
//                // Если закончились вопросы по квизу - начинаем отображать вопросы с видео
//                question = getPlainQuestion(theme, 1);
//            }
        }
        if("plain".equals(type)){
            question = getPlainQuestion(theme, num);
        }
        return question;
    }

    public static PlainQuestion getPlainQuestion(String theme, Integer num) throws Exception{
        try(Connection conn = MyDataSourceFactory.getConnection()){
            Statement st = conn.createStatement();
            String sql = String.format("select TEXT, ANSWER, NUM from QUESTION where THEME='%s' and NUM=%d", theme, num);
            logger.debug(String.format("executeQuery: %s", sql));
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                PlainQuestion question = new PlainQuestion(rs.getString("TEXT"), theme, rs.getString("Answer"), rs.getInt("NUM"));
                logger.debug(String.format("Received plain question: %s", question.toJSON().toString()));
                return question;
            }
            else{
                return null;
            }
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public static QuizQuetion getQuizQuestion(String theme, Integer num) throws Exception{
        try(Connection conn = MyDataSourceFactory.getConnection()){
            Statement st = conn.createStatement();
            String sql = String.format("select ID, TEXT, NUM, ANSWER_NUM from QUIZ where THEME='%s' and NUM=%d", theme, num);
            logger.debug(String.format("executeQuery: %s", sql));
            ResultSet rs = st.executeQuery(sql);
            QuizQuetion question = null;
            if(rs.next()){
                question = new QuizQuetion(rs.getInt("id"), rs.getString("TEXT"), theme, rs.getInt("NUM"), rs.getInt("ANSWER_NUM"));
                logger.debug(String.format("Received quiz question: %s", question.toJSON().toString()));
            }
            else{
                return null;
            }
            sql = String.format("select TEXT, NUM from QUIZ_OPT where QUIZ_ID=%d", question.getId());
            logger.debug(String.format("executeQuery: %s", sql));
            rs = st.executeQuery(sql);
            while(rs.next()){
                QuizOption option = new QuizOption(rs.getString("TEXT"), rs.getInt("NUM"));
                question.add(option);
            }
            return question;
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
    }
}
