package com.liquibase.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ldt merry
 * @date 2019/10/23
 */
@SpringBootApplication
@MapperScan(basePackages ="com.liquibase*")
public class LiquibaseApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }



}
