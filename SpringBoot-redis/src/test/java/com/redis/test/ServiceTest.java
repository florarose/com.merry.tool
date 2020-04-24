package com.redis.test;

import com.redis.study.RedisApplication;
import com.redis.study.service.GoodsService;
import com.redis.study.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liudongting
 * @date 2019/7/29 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes  = RedisApplication.class)
public class ServiceTest {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Test
    public void usertest(){
         userService.getUser(4);
    }


}
