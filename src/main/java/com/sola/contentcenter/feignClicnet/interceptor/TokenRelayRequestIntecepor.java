package com.sola.contentcenter.feignClicnet.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign拦截器 将请求token放入到feign请求中
 */
public class TokenRelayRequestIntecepor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取tiken
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader("X-Token");

        if (StringUtils.isNotBlank(token)) {
            requestTemplate.header("X-Token", token);
        }
    }
}
