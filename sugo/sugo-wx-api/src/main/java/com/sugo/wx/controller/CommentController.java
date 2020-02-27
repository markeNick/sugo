package com.sugo.wx.controller;

import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoComment;
import com.sugo.sql.service.GoodsService;
import com.sugo.sql.service.TopicService;
import com.sugo.sql.service.CommentService;
import com.sugo.sql.service.UserService;
import com.sugo.wx.annotation.LoginUser;
import com.sugo.wx.entity.UserInfo;
import com.sugo.wx.service.UserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户评论
 */
@RestController
@RequestMapping("/wx/comment")
@Validated
public class CommentController {

    private final Log logger = LogFactory.getLog(CommentController.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TopicService topicService;

    /**
     * 发表评论
     *
     * @param userId
     * @param comment
     * @return
     */
    @PostMapping("post")
    public Object post(@LoginUser Integer userId, @RequestBody SugoComment comment) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Object error = validate(comment);
        if (error == null) {
            return error;
        }

        comment.setUserId(userId);
        commentService.save(comment);
        return ResponseUtil.ok(comment);
    }

    /**
     * 评论数量
     * @param type
     * @param valueId
     * @return
     */
    @GetMapping("count")
    public Object count(@NotNull Byte type, @NotNull Integer valueId) {
        // 统计总评论数
        int allCount = commentService.count(type, valueId, 0);
        // 统计含有图片的评论数
        int hasPictCount = commentService.count(type, valueId, 1);

        Map<String, Object> data = new HashMap<>();
        data.put("allCount", allCount);
        data.put("hasPictCount", hasPictCount);

        return ResponseUtil.ok(data);
    }

    /**
     * 评论列表
     * @param type
     * @param valueId
     * @param showType
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public Object list(@NotNull Byte type,
                       @NotNull Integer valueId,
                       @NotNull Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {

        List<SugoComment> commentList = commentService.query(type, valueId, showType, page, limit);
        List<Map<String, Object>> data = new ArrayList<>(commentList.size());
        for (SugoComment comment : commentList) {
            Map<String, Object> c = new HashMap<>();
            c.put("addTime", comment.getAddTime());
            c.put("content", comment.getContent());
            c.put("adminContent", comment.getAdminContent());
            c.put("picList", comment.getPicUrls());

            UserInfo userInfo = userInfoService.getInfo(comment.getUserId());
            c.put("userInfo", userInfo);

            data.add(c);
        }

        return ResponseUtil.okList(data, commentList);
    }



    private Object validate(SugoComment comment) {
        String content = comment.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }

        Short star = comment.getStar();
        if (star == null) {
            return ResponseUtil.badArgument();
        }
        if (star < 0 || star > 5) {
            return ResponseUtil.badArgumentValue();
        }

        Byte type = comment.getType();
        Integer valueId = comment.getValueId();
        if (type == null || valueId == null) {
            return ResponseUtil.badArgument();
        }
        if (type == 0) {
            if (goodsService.findById(valueId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        } else {
            return ResponseUtil.badArgumentValue();
        }
        Boolean hasPicture = comment.getHasPicture();
        if (hasPicture == null || !hasPicture) {
            comment.setPicUrls(new String[0]);
        }
        return null;
    }
}





















