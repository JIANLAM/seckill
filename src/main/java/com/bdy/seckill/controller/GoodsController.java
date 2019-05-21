package com.bdy.seckill.controller;

import com.bdy.seckill.domain.MiaoshaUser;
import com.bdy.seckill.domain.vo.GoodsVo;
import com.bdy.seckill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: linzj
 * @Date: 2019-05-08 22:51
 * @Description 商品模块的控制层
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model,MiaoshaUser user){
        model.addAttribute("user",user);
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("goodsVoList",goodsVoList);
        return "goods_list";
    }
}
