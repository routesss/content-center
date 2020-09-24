package com.sola.contentcenter.controller.share;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.sola.contentcenter.controller.util.IResult;
import com.sola.contentcenter.controller.util.Result;
import com.sola.contentcenter.domain.dto.share.ShareDto;
import com.sola.contentcenter.domain.dto.user.UserDto;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.service.share.IShareService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 分享表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-09-21
 */
@Slf4j
@RestController
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IShareService shareService;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/test")
    public String testInsert() {

        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        log.info(JSONObject.toJSONString(instances));
        /*Share share = new Share();
        share.setTitle("芦荟制作手册");
        share.setBuyCount(2);
        share.setCover("XXX");
        share.setCreateTime(LocalDateTime.now());
        share.setUpdateTime(LocalDateTime.now());
        
        shareService.save(share);
        
        List<Share> list = shareService.list();
        System.out.println(JSONObject.toJSONString(list));
        log.info("分享信息 : {}", JSONObject.toJSONString(list));*/

        return "OK";
    }

    @RequestMapping("/{id}")
    public IResult findShareById(@PathVariable("id") Integer shareId) {
        Share share = shareService.getById(shareId);
        log.info("分享信息 : {}", JSONObject.toJSONString(share));

        UserDto userDto =
            restTemplate.getForObject("http://127.0.0.1:8080/user/{id}", UserDto.class, share.getUserId());
        log.info("用户信息 : {}", JSONObject.toJSONString(userDto));

        ShareDto shareDto = new ShareDto();
        BeanUtils.copyProperties(share, shareDto);
        shareDto.setWxNickname(userDto != null ? userDto.getWxNickname() : "");

        return Result.ok(shareDto);
    }

}
