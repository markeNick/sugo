package com.sugo.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.sugo.common.notify.NotifyService;
import com.sugo.common.util.IpUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoUser;
import com.sugo.sql.service.CouponAssignService;
import com.sugo.sql.service.UserService;
import com.sugo.wx.annotation.LoginUser;
import com.sugo.wx.entity.UserInfo;
import com.sugo.wx.entity.WxLoginInfo;
import com.sugo.wx.service.UserTokenManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理微信登录
 */
@RestController
@RequestMapping("/wx/auth")
@Validated
public class AuthController {
    private final Log logger = LogFactory.getLog(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private CouponAssignService couponAssignService;

    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (userInfo == null || code == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openID = null;

        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openID = result.getOpenid();
        } catch (Exception e) {
            logger.debug("微信登录失败：error-> ", e);
        }

        if (sessionKey == null || openID == null) {
            return ResponseUtil.fail();
        }

        SugoUser user = userService.queryByOpenId(openID);
        if (user == null) {
            user = new SugoUser();
            user.setUsername(openID);
            user.setPassword(openID);
            user.setWeixinOpenid(openID);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);

            userService.add(user);

            // 新用户发送注册优惠券
            couponAssignService.assignForRegister(user.getId());
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        // 生成token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 退出登录
     * @param userId
     * @return
     */
    @PostMapping("logout")
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

    /**
     * 用户信息
     * @param userId
     * @return
     */
    @GetMapping("info")
    public Object info(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

       SugoUser user = userService.findById(userId);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("nickName", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());

        return ResponseUtil.ok(data);
    }
}