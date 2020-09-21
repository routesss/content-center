package com.sola.contentcenter.controller.share;


import com.alibaba.fastjson.JSONObject;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.service.share.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 分享表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/share")
public class ShareController {

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

        return "OK";
    }
}