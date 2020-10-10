package com.sola.contentcenter.config;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

import com.sola.ribbon.config.RibbonConfiguration;

/**
 * 全局配置ribbon 负载规则
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class RibbonRuleConfiguration {}
