package com.liquibase.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquibase.demo.model.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author ldt merry
 * @date 2019/10/23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
