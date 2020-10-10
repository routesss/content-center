package com.sola.contentcenter.controller.util;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * 获取服务的uri信息
 */
@Configuration
public class ServiceUriUtil {

    @Autowired
    private DiscoveryClient discoveryClient;

    public URI getUri(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.size() != 0) {
            return instances.get(RandomUtil.nextInt(instances.size())).getUri();
        } else {
            return null;
        }
    }
}
