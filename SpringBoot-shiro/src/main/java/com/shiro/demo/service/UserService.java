package com.shiro.demo.service;

import com.shiro.demo.entity.User;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public interface UserService {

    User getUserByName(String userName);
}
