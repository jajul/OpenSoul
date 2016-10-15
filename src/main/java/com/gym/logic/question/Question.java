package com.gym.logic.question;

/**
 * Created by Gochan on 15.10.2016.
 */
public class Question {
    private String text;
    private String answer;
    private Integer num;

    public Question(String text, String answer, Integer num) {
        this.text = text;
        this.answer = answer;
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
