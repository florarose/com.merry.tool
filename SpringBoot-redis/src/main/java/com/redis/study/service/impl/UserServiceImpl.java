package com.redis.study.service.impl;



import com.redis.study.mapper.UserMapper;
import com.redis.study.pojo.User;
import com.redis.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(int id) {
        return userMapper.selectOne(4);
    }
}
