package com.sola.contentcenter.feignClicnet;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sola.contentcenter.domain.dto.user.UserDto;

/**
 * 用户中心的feign client 配置【configuration = GlobalFeigenConfiguration.class中的配置的日志级别需要在spring配置文件中需要配置该文件日志级别为debug才有效果】
 * 
 * @FeignClient(name = "user-center", configuration = GlobalFeigenConfiguration.class)
 */
@FeignClient(name = "user-center")
public interface UserCenterClient {

    @GetMapping("/user/{id}")
    UserDto findById(@PathVariable(value = "id") Integer id);

    @GetMapping("/user/queryUser")
    List<UserDto> queryUser(@SpringQueryMap UserDto user);
}
