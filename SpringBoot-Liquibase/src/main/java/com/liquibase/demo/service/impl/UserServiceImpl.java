package com.liquibase.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liquibase.demo.mapper.UserMapper;
import com.liquibase.demo.model.User;
import com.liquibase.demo.service.UserService;
import org.springframework.stereotype.Service;



/**
 * @author ldt merry
 * @date 2019/10/23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
