package com.sola.contentcenter.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;

/**
 * 用户中心feigen配置类
 */
public class GlobalFeigenConfiguration {

    /**
     * 配置feigen日志级别
     * 
     * @return
     */
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
