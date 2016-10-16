package com.gym.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julia on 16.10.2016.
 */
public class UserInfo {
    private static volatile Map<String, User> userInfoMap =new HashMap<>();

    public static User getUser(String login, String name, String email){
        if (userInfoMap.get(login) == null){
            userInfoMap.put(login, new User(name, email));

        }
        return userInfoMap.get(login);
    }

    public static User getUser(String login){
        return userInfoMap.get(login);
    }
}
