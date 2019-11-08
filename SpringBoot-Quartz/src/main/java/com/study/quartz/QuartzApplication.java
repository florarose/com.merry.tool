package com.study.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/8 16:40
 */
@SpringBootApplication
@MapperScan(basePackages ="com.study.quartz.*")
public class QuartzApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

}
