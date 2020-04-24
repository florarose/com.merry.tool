package com.redis.study.mapper;

import com.redis.study.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

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
}
