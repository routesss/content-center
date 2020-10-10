package com.sola.ribbon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.sola.contentcenter.config.NacosSameClusterWeightedRule;

/**
 * 配置ribbon 负载规则
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        /*return new NacosWeightedRule();*/
        return new NacosSameClusterWeightedRule();
    }
}
