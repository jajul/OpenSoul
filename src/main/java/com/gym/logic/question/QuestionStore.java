package com.gym.logic.question;

import com.gym.db.MyDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Gochan on 15.10.2016.
 */
public class QuestionStore {
    private static final Logger logger = LogManager.getLogger(QuestionStore.class);

    public static Question getRandomQuestion() throws Exception{
        try(Connection conn = MyDataSourceFactory.getConnection()){
            Statement st = conn.createStatement();
            String sql = "select TEXT, ANSWER, NUM from QUESTION";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return new Question(rs.getString("TEXT"), rs.getString("Answer"), rs.getInt("NUM"));
            }
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        return null;
    }

    public static Question getQuestion(Integer num) throws Exception{
        try(Connection conn = MyDataSourceFactory.getConnection()){
            Statement st = conn.createStatement();
            String sql = String.format("select TEXT, ANSWER from QUESTION where NUM=%d", num);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                return new Question(rs.getString("TEXT"), rs.getString("Answer"), rs.getInt("NUM"));
            }
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        return null;
    }
}
