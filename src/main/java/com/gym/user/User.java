package com.gym.user;

import org.json.JSONObject;

/**
 * Created by Julia on 16.10.2016.
 */
public class User {
    private String userName;
    private String userEmail;
    private UserQuizResult quizResult;

    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;

        this.quizResult = new UserQuizResult();
    }

    public UserQuizResult getQuizResult() {
        return quizResult;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public JSONObject toJSON(){
        return new JSONObject().put("userName", userName).put("userEmail", userEmail);
    }
    @Override
    public String toString() {
        return toJSON().toString();
    }
}
