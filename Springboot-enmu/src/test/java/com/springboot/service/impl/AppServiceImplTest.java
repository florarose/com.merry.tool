package com.springboot.service.impl;

import com.springboot.enmu.entity.App;
import com.springboot.enmu.service.appService;
import com.springboot.service.TestServiceCOnfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @author liudongting
 * @date 2019/8/20 16:35
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestServiceCOnfiguration.class)


//// SpringJUnit支持，由此引入Spring-Test框架支持！

@RunWith(SpringJUnit4ClassRunner.class)



//// 指定我们SpringBoot工程的Application启动类




///由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@SpringBootTest(classes = TestServiceCOnfiguration.class)
@WebAppConfiguration
public class AppServiceImplTest {

    @Resource
    private appService userService;

    @Test
    public void getUserByName() {
        App app = userService.getApp(1);
        System.out.println(app);
    }
}