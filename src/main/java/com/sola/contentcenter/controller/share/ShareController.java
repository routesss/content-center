package com.sola.contentcenter.controller.share;


import com.alibaba.fastjson.JSONObject;
import com.sola.contentcenter.controller.util.Result;
import com.sola.contentcenter.domain.dto.user.UserDto;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.service.share.IShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

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

    @RequestMapping("/test")
    public String testInsert(){

        Share share = new Share();
        share.setTitle("芦荟制作手册");
        share.setBuyCount(2);
        share.setCover("XXX");
        share.setCreateTime(LocalDateTime.now());
        share.setUpdateTime(LocalDateTime.now());

        shareService.save(share);

        List<Share> list = shareService.list();
        System.out.println(JSONObject.toJSONString(list));
        log.info("分享信息 : {}", JSONObject.toJSONString(list));


        return "OK";
    }

    @RequestMapping("/{id}")
    public void findShareById(@PathVariable("id") Integer shareId){
        Share share = shareService.getById(shareId);
        log.info("分享信息 : {}", JSONObject.toJSONString(share));

        UserDto userDto = restTemplate.getForObject("http://127.0.0.1:8080/user/{id}", UserDto.class, 2);
        log.info("用户信息 : {}",JSONObject.toJSONString(userDto));

    }

}
