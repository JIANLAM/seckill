package com.bdy.seckill.config;

import com.alibaba.druid.util.StringUtils;
import com.bdy.seckill.domain.MiaoshaUser;
import com.bdy.seckill.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: linzj
 * @Date: 2019-05-12 14:24
 * @Description 统一拦截秒杀user的token
 */
@Service
public class myArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService  miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String parameterToken = request.getParameter(MiaoshaUserService.COOIE_NAME_TOKEN);
        String cookieToken = getCookieToken(request,MiaoshaUserService.COOIE_NAME_TOKEN);

        if (StringUtils.isEmpty(parameterToken) && StringUtils.isEmpty(cookieToken)) return  null;

        String token = StringUtils.isEmpty(parameterToken) ? cookieToken : parameterToken;

        MiaoshaUser user = miaoshaUserService.getUserByToken(response,token);
        return user;
    }

    private String getCookieToken(HttpServletRequest request, String cooieNameToken) {

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cooieNameToken)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
