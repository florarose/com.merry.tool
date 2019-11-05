package com.study.thread;

import com.baomidou.mybatisplus.extension.api.R;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 10:48
 */
@SpringBootApplication
@MapperScan(basePackages ="com.study*")
public class ThreadApplication  implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(ThreadApplication.class, args);
    }

    // springboot运行后此方法首先被调用
    // 实现CommandLineRunner抽象类中的run方法
    public void run(String... args) throws Exception {
//        OrderUnPayTimerService orderUnPayTimerService = new OrderUnPayTimerService();
//        orderUnPayTimerService.doBiz();
    }
}
