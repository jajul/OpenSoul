package com.gym.user;

import com.gym.logic.question.Question;
import com.gym.logic.question.QuizQuetion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julia on 16.10.2016.
 */
public class UserQuizResult {
    private static final Logger logger = LogManager.getLogger(UserQuizResult.class);

    private Map<Question, Integer> quizResults = new HashMap<>();

    public Map<Question, Integer> addQuestion(Question question) {
        this.quizResults.put(question, null);
        return quizResults;
    }

    public Map<Question, Integer> addResult(Question question, Integer answer) {
        for (Question q : quizResults.keySet()) {
            if (q.getNum() == question.getNum() && q.getTheme().equals(question.getTheme())) {
                quizResults.remove(q);
            }
        }

        this.quizResults.put(question, answer);
        logger.debug(String.format("Put to quizResults question %s and answer %d", question.getTheme()+"-"+question.getNum(), answer));
        return quizResults;
    }

    public Map<Question, Integer> getQuizResults() {
        return quizResults;
    }

    public Double getTestResult() {
        try {
            int count = quizResults.size();
            if (count == 0) {
                return null;
            }
            int rightAnswers = 0;
            for (Question q : quizResults.keySet()) {
                QuizQuetion quizQuetion = (QuizQuetion) q;
                if (quizQuetion.getAnswer_num().equals(quizResults.get(q))) {
                    rightAnswers++;
                }
            }
            return Double.valueOf(rightAnswers) / count;
        } catch (Exception e) {
            return null;
        }
    }
}
