package com.study.rabbitmq.service;

import com.study.rabbitmq.pojo.Goods;

import java.util.List;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/24 15:11
 */
public interface GoodsService {


    public Goods searchArticleById(Integer id);

    public List<Goods>  getAll();

    void insertGoods(Goods goods);

    void updateGoods(Goods goods);
}
