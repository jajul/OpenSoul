package com.gym.logic.question;

import org.json.JSONObject;

/**
 * Created by Gochan on 16.10.2016.
 */
public abstract class Question {
    public abstract JSONObject toJSON();

    public abstract String getTheme();

    public abstract Integer getNum();

    public abstract String getText();
}
