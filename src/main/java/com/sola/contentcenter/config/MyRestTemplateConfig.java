package com.sola.contentcenter.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyRestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate templateConfig() {
        return new RestTemplate();
    }
}
