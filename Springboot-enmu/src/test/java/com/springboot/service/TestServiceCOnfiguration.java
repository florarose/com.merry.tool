package com.springboot.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


/**
 * @author liudongting
 * @date 2019/8/20 16:40
 */
@ComponentScan(basePackages = {
        "com.springboot.enmu"
})
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class TestServiceCOnfiguration {
    public static void main(String [] args){
        SpringApplication.run(TestServiceCOnfiguration.class,args);
    }
}
