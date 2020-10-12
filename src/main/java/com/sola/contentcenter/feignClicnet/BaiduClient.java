package com.sola.contentcenter.feignClicnet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * feignclient指定url使用自定义的服务地址  脱离ribbon的使用
 */
@FeignClient(value = "baidu", url = "https://www.baidu.com/")
public interface BaiduClient {

    @GetMapping("")
    public String index();
}
