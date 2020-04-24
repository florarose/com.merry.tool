package com.redis.study.service;

import com.redis.study.pojo.Goods;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/24 15:11
 */
public interface GoodsService {


    public Goods searchArticleById(Integer id);
}
