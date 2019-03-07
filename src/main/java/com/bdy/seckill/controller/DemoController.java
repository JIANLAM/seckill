package com.bdy.seckill.controller;

import com.bdy.seckill.common.Result;
import com.bdy.seckill.domain.User;
import com.bdy.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/thymeleaf")
    public String getIndex(){
        return "index";
    }


    @RequestMapping("/getData")
    @ResponseBody
    public Result<User> getData(){
        User user = userService.getDataById(1L);
        return Result.success(user);
    }
}
