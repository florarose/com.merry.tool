package com.study.rabbitmq.controller;


import com.study.rabbitmq.service.GoodsService;
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
        System.out.println(goodsService.searchArticleById(1));
    }




}
