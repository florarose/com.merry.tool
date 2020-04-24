package test.controller.test;


import com.study.rabbitmq.RabbitmqApplication;
import com.study.rabbitmq.config.RabbitConfig;
import com.study.rabbitmq.pojo.Goods;
import com.study.rabbitmq.pojo.User;
import com.study.rabbitmq.service.GoodsService;
import com.study.rabbitmq.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liudongting
 * @date 2019/7/29 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes  = RabbitmqApplication.class)
public class ServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private GoodsService goodsService;

    @Test
    public void usertest(){
        User user = new User();
        user.setUsername("merry");
        user.setPassword("123456");
        userService.add(user);
    }
    @Test
    public void rabbitTest(){
        RabbitTemplate rabbitTemplate = rabbitConfig.rabbitTemplate();
        System.out.println(rabbitTemplate);
    }
    @Test
    public void listAll(){
     goodsService.getAll();
        System.out.println(goodsService.getAll());
    }
    @Test
    public void insertGoods(){
        Goods goods = new Goods();
        goods.setType(1);
        goods.setContent("男装");
        goods.setName("清凉男装");
        goodsService.insertGoods(goods);
    }
}
