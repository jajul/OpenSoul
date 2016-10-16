package com.gym.logic.question;

import org.json.JSONObject;

/**
 * Created by Gochan on 16.10.2016.
 */
public class QuizOption {
    private String text;
    private Integer num;

    public QuizOption(String text, Integer num) {
        this.text = text;
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("text", text);
        json.put("num", num);
        return json;
    }
}
