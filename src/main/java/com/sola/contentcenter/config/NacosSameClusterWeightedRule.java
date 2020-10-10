package com.sola.contentcenter.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            // 当前cluster名
            String clusterName = nacosDiscoveryProperties.getClusterName();

            // 获取微服务服务名
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer)this.getLoadBalancer();

            // 获取服务发现api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // 获取指定的所有实例信息
            List<Instance> instances = namingService.selectInstances(loadBalancer.getName(), true);

            // 过滤当前cluster的实例
            List<Instance> collect =
                instances.stream().filter(instance -> Objects.equals(instance.getClusterName(), clusterName))
                    .collect(Collectors.toList());

            List<Instance> instancesToBeChosen = new ArrayList<>();
            if (CollectionUtils.isEmpty(collect)) {
                instancesToBeChosen = instances;
                log.info("跨集群调用, name={}, clusterName={},instances={}", loadBalancer.getName(), clusterName, instances);
            } else {
                instancesToBeChosen = collect;
            }
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToBeChosen);
            log.info("选择的实例信息  port = {}  weight = {}, clusterName={}", instance.getPort(), instance.getWeight(),
                instance.getClusterName());

            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class ExtendBalancer extends Balancer {
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}