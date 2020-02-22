package com.sugo.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {
    @Autowired
    private WxDetails details;

    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(details.getAppId());
        config.setSecret(details.getAppSecret());
        return config;
    }


    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }

    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(details.getAppId());
        payConfig.setMchId(details.getMchId());
        payConfig.setMchKey(details.getMchKey());
        payConfig.setNotifyUrl(details.getNotifyUrl());
        payConfig.setKeyPath(details.getKeyPath());
        payConfig.setTradeType("JSAPI");
        payConfig.setSignType("MD5");
        return payConfig;
    }


    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}