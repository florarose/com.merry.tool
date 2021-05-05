package com.redis.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/24 14:28
 */
@SpringBootApplication
@MapperScan("com.redis.study.mapper")
@EnableScheduling
public class RedisApplication {

    /**
     * DPAWFAITIHLTKHZT
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
