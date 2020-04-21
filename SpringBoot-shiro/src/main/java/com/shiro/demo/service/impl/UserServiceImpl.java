package com.shiro.demo.service.impl;

import com.shiro.demo.entity.User;
import com.shiro.demo.service.UserService;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:51
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByName(String userName) {
        User user = new User();
        return user;
    }
}
