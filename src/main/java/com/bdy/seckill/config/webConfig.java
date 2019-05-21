package com.bdy.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: linzj
 * @Date: 2019-05-12 14:21
 * @Description 统一的web拦截器
 */

@Configuration
public class webConfig extends WebMvcConfigurerAdapter {

    @Autowired
    myArgumentResolver myArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(myArgumentResolver);
    }
}
