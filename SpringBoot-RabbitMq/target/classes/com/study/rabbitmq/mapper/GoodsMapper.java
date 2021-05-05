package com.study.rabbitmq.mapper;

import com.study.rabbitmq.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/24 15:19
 */
@Mapper
public interface GoodsMapper {
    Goods  selectGoods(@Param("id") Integer id);
    List<Goods> selectAll();
    void insert(Goods goods);
    void update(Goods goods);
}
