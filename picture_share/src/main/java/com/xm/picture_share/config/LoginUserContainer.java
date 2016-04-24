package com.xm.picture_share.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 已登录用户列表
 */
public class LoginUserContainer {

    private static ConcurrentHashMap<String, Long> loginUserContainer = new ConcurrentHashMap<String, Long>();

    public static Long getLoginUser(String token) {
        if (!loginUserContainer.containsKey(token)) {
            return null;
        } else {
            return loginUserContainer.get(token);
        }
    }

    public static void addLoginUser(String token, Long userId) {
        loginUserContainer.put(token, userId);
    }
}
