package com.sola.contentcenter.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            // 获取微服务服务名
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer)this.getLoadBalancer();

            // 获取服务发现api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // 通过nacos获取服务
            Instance instance = namingService.selectOneHealthyInstance(loadBalancer.getName());

            log.info("选择的实例信息  port = {}  weight = {}", instance.getPort(), instance.getWeight());

            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}
