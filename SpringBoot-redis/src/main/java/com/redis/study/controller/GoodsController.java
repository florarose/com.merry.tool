package com.redis.study.controller;


import com.redis.study.pojo.Goods;
import com.redis.study.service.GoodsService;
import com.redis.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("good")
    public void getGoods(){
        goodsService.searchArticleById(1);
    }




}
