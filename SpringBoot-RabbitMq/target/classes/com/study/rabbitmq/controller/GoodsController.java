package com.study.rabbitmq.controller;


import com.study.rabbitmq.pojo.Goods;
import com.study.rabbitmq.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("updateGoods")
    public void updateGoods(@RequestBody Goods goods){
        goodsService.updateGoods(goods);
    }


}
