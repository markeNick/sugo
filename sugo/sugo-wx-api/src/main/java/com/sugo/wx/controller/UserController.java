package com.sugo.wx.controller;

import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.service.order.OrderService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/user")
@Validated
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 用户订单统计信息 ———— 用于个人中心的 我的订单的小红点
     *
     * @param userId
     * @return
     */
    @GetMapping("index")
    public Object index(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<>();
        data.put("order", orderService.orderInfo(userId));

        return ResponseUtil.ok(data);
    }
}