package com.gym.logic.question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gochan on 16.10.2016.
 */
public class QuizQuetion extends Question {
    private int id;
    private String text;
    private String theme;
    private Integer num;
    private Integer answer_num;
    private List<QuizOption> options;

    public QuizQuetion(int id, String text, String theme, Integer num, Integer answer_num) {
        this.id = id;
        this.text = text;
        this.theme = theme;
        this.num = num;
        this.answer_num = answer_num;
        options = new ArrayList<QuizOption>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(Integer answer_num) {
        this.answer_num = answer_num;
    }

    public void add(QuizOption option){
        options.add(option);
    }

    public QuizOption get(Integer num){
        for(QuizOption option : options){
            if(option.getNum() == num){
                return option;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("type", "quiz");
        json.put("text", text);
        json.put("theme", theme);
        json.put("num", num);
        json.put("answer_num", answer_num);
        JSONArray opts = new JSONArray();
        for(QuizOption option : options){
            opts.put(option.toJSON());
        }
        json.put("options", opts);
        return json;
    }
}
