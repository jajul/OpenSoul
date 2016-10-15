package com.gym.logic.question;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gochan on 15.10.2016.
 */
public class QuestionStoreTest {

    //@Test
    public void testGetRandomQuestion() throws Exception {
        Question question = QuestionStore.getRandomQuestion();
        assert (question != null);
        assert (question.getText() != null);
    }

    //@Test
    public void testGetQuestion() throws Exception {

    }
}