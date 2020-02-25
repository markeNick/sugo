package com.sugo.wx.service;

import com.sugo.sql.entity.SugoUser;
import com.sugo.sql.service.user.UserService;
import com.sugo.wx.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private UserService userService;

    public UserInfo getInfo(Integer uid) {
        SugoUser user = userService.findById(uid);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }
}