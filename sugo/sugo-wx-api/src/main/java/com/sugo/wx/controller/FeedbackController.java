package com.sugo.wx.controller;

import com.sugo.common.util.RegexUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoFeedback;
import com.sugo.sql.entity.SugoUser;
import com.sugo.sql.service.FeedbackService;
import com.sugo.sql.service.UserService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈
 */
@RestController
@RequestMapping("/wx/feedback")
@Validated
public class FeedbackController {
    private final Log logger = LogFactory.getLog(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserService userService;


    /**
     * 提交意见反馈
     *
     * @param userId
     * @param feedback
     * @return
     */
    @PostMapping("submit")
    public Object submit(@LoginUser Integer userId, @RequestBody SugoFeedback feedback) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(feedback);
        if (error != null) {
            return error;
        }

        SugoUser user = userService.findById(userId);
        String username = user.getUsername();
        feedback.setId(null);
        feedback.setUserId(userId);
        feedback.setUsername(username);
        //状态默认是0，1表示状态已发生变化
        feedback.setStatus(1);
        feedbackService.add(feedback);

        return ResponseUtil.ok();
    }

    private Object validate(SugoFeedback feedback) {
        String content = feedback.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }

        String type = feedback.getFeedType();
        if (StringUtils.isEmpty(type)) {
            return ResponseUtil.badArgument();
        }

        Boolean hasPicture = feedback.getHasPicture();
        if (hasPicture == null || !hasPicture) {
            feedback.setPicUrls(new String[0]);
        }

        // 测试手机号码是否正确
        String mobile = feedback.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}