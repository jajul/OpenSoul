package com.gym.logic.question;

import org.json.JSONObject;

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

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("text", this.text);
        json.put("num", this.num);
        return json;
    }
}
