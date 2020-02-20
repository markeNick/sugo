package com.sugo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {
    private long maxDay = 30 * 24 * 60 * 60;        // 当前跨域请求最大有效天数

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    // 设置访问源地址
        corsConfiguration.addAllowedHeader("*");    // 设置访问源请求头
        corsConfiguration.addAllowedMethod("*");    // 设置访问源请求方法
        corsConfiguration.setMaxAge(maxDay);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());     // 对接口配置跨域设置
        return new CorsFilter(source);
    }
}