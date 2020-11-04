package com.redis.study.service.impl;

import com.redis.study.config.JedisUtil;
import com.redis.study.mapper.GoodsMapper;
import com.redis.study.pojo.Goods;
import com.redis.study.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 坎布里奇
* @version 1.0
* @date 2020/4/24 15:11
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods searchArticleById(Integer id){

        Object object = jedisUtil.get(String.valueOf(id));
        if(object != null){// 缓存查询到了结果
            return (Goods)object;
        }
        // 开始查询数据库
        Goods goods = goodsMapper.selectGoods(id);
        if(goods!=null){
            // 将结果保存到缓存中
            jedisUtil.set(String.valueOf(id),"11",300);
        }
        return goods;
    }

}
