package com.springboot.enmu.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.enmu.entity.App;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppMapper  extends BaseMapper<App> {
    App getApp(@Param("id") int id);
}
