package com.shiro.demo.entity;

import lombok.Data;



/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
@Data
public class User {

    private String userName;
    private String password;
    private String salt;
}
