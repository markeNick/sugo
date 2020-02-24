package com.sugo.wx.service;

import com.sugo.wx.util.JwtHelper;

public class UserTokenManager {
    public static String generateToken(Integer id) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id);
    }

    public static Integer getUserId(String token) {
        JwtHelper jwtHelper = new JwtHelper();
        Integer uid = jwtHelper.verifyTokenAndGetUserId(token);

        if(uid == null || uid == 0) return null;
        return uid;
    }
}