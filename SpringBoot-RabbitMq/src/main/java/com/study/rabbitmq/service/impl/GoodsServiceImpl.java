package com.study.rabbitmq.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.study.rabbitmq.mapper.GoodsMapper;
import com.study.rabbitmq.pojo.Goods;
import com.study.rabbitmq.service.GoodsService;
import com.study.rabbitmq.util.JedisUtil;
import com.study.rabbitmq.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        String object = jedisUtil.get(String.valueOf(id));
        if(object != null){// 缓存查询到了结果
            Goods goods = JsonUtil.strToObj(object,Goods.class);
            return goods;
        }
        // 开始查询数据库
        Goods goods = goodsMapper.selectGoods(id);
        if(goods!=null){
            // 将结果保存到缓存中
            jedisUtil.set(String.valueOf(id), JsonUtil.objToStr(goods),300);
        }else {
            // 这个else 是为了防止缓存穿透， 缓存穿透，是指查询一个数据库一定不存在的数据。
            //  采用缓存空值的方式，如果从数据库查询的对象为空，也放入缓存，只是设定的缓存过期时间较短，比如设置为60秒。
            jedisUtil.set(String.valueOf(id), null,60);
        }
        return goods;
    }

    @Override
    public List<Goods> getAll() {
        List<String> stringList = jedisUtil.lrange("listRecord",100000);
        if(null != stringList && stringList.size() != 0){
            String jsonString =JsonUtil.objToStr(stringList);
            List<Goods> goodsList = JSONArray.parseArray(jsonString,Goods.class);
            return goodsList;
        }
        List<Goods> goodsList = goodsMapper.selectAll();
        for(Goods goods : goodsList){
            jedisUtil.lpush("listRecord",JsonUtil.objToStr(goods));
        }
        return goodsMapper.selectAll();
    }

    @Override
    public void insertGoods(Goods goods) {
         goodsMapper.insert(goods);
    }

    @Override
    public void updateGoods(Goods goods) {
          goodsMapper.update(goods);
    }
/**
     * 缓存方式
     *   缓存中的数据在redis中的存储方式有两种，一种是永久存在，不设置过期时间，第二种是设置过期时间。这两种方式都需要尽可能的保证数据的一致性(和数据源中的数据保持同步)。
     */

}
