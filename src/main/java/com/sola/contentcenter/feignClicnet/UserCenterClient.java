package com.sola.contentcenter.feignClicnet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sola.contentcenter.domain.dto.user.UserDto;

@FeignClient(name = "user-center")
public interface UserCenterClient {

    @GetMapping("/user/{id}")
    UserDto findById(@PathVariable Integer id);
}
