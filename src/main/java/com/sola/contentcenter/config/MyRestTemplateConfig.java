package com.sola.contentcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyRestTemplateConfig {

    @Bean
    public RestTemplate templateConfig(){
        return new RestTemplate();
    }
}
