package com.sugo.wx.controller;

import com.sugo.common.util.ResponseUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class MyController {

    @GetMapping("/wx")
    public Object test() {

        return ResponseUtil.ok();
    }
}