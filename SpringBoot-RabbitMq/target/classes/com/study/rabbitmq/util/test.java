package com.study.rabbitmq.util;

import com.study.rabbitmq.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/3/7 20:41
 * @version: 1.0.0
 */
public class test {

    public static void main(String [] args){
        List<User> userArrrayList = new ArrayList<User>();
        User u = new User();
        u.setId(1);
        u.setUserName("11111");
        userArrrayList.add(u);
        User u2 = new User();
        u2.setId(2);
        userArrrayList.add(u2);
        userArrrayList.forEach(ser ->{
            if(ser.getId() == 1){
                return;
            }
            System.out.println(ser);
        });
    }
}
