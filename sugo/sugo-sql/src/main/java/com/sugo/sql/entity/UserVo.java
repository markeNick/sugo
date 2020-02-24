package com.sugo.sql.entity;

public class UserVo {
    private String nickname;    // 昵称
    private String avatar;      // 头像

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}