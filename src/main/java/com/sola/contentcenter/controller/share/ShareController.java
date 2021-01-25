package com.sola.contentcenter.controller.share;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.sola.contentcenter.controller.util.IResult;
import com.sola.contentcenter.controller.util.Result;
import com.sola.contentcenter.controller.util.ServiceUriUtil;
import com.sola.contentcenter.domain.dto.share.ShareDto;
import com.sola.contentcenter.domain.dto.user.UserDto;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.feignClicnet.BaiduClient;
import com.sola.contentcenter.feignClicnet.UserCenterClient;
import com.sola.contentcenter.service.notice.INoticeService;
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
    private ServiceUriUtil serviceUriUtil;
    @Autowired
    private UserCenterClient userCenterClient;
    @Autowired
    private INoticeService noticeService;

    @RequestMapping("/test")
    public String testInsert() {

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

    @RequestMapping("/getUser")
    public IResult getUser(UserDto userDto) {
        List<UserDto> userDtos = userCenterClient.queryUser(userDto);

        return Result.ok(userDtos);
    }

    @Autowired
    private BaiduClient baiduClient;

    @GetMapping("/baidu")
    public String baiduTest() {
        return baiduClient.index();
    }

    @RequestMapping("/{id}")
    public IResult findShareById(@PathVariable("id") Integer shareId) {
        Share share = shareService.getById(shareId);
        if (share == null) {
            return Result.error("分享内容不存在");
        }

        /* 1
        URI uri = serviceUriUtil.getUri("user-center");
        if (uri == null) {
            return Result.error("服务不存在");
        }
        UserDto userDto = restTemplate.getForObject(uri.toString() + "/user/{id}", UserDto.class, share.getUserId());
        */

        /*UserDto userDto = restTemplate.getForObject("http://user-center/user/{id}", UserDto.class, share.getUserId());*/

        UserDto userDto = userCenterClient.findById(share.getUserId());

        log.info("用户信息 : {}", JSONObject.toJSONString(userDto));

        ShareDto shareDto = new ShareDto();
        BeanUtils.copyProperties(share, shareDto);
        shareDto.setWxNickname(userDto != null ? userDto.getWxNickname() : "");

        return Result.ok(shareDto);
    }

}
