package com.springboot.enmu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.PrintStream;

/**
 * @author liudongting
 * @date 2019/8/19 14:26
 */
@ComponentScan(basePackages = "com.springboot")
@SpringBootApplication
@Slf4j
public class ImagApplication extends SpringBootServletInitializer implements CommandLineRunner {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ImagApplication.class);
    }

    public static void main(String [] args){
        SpringApplication.run(ImagApplication.class, args);
    }

    // springboot运行后此方法首先被调用
    // 实现CommandLineRunner抽象类中的run方法
    //启动完成后执行的方法
    public void run(String... args) throws Exception {
        log.error("启动完成！");
    }

}
