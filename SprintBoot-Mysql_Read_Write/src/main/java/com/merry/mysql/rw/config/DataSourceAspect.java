package com.merry.mysql.rw.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/11 14:18
 * @Description: 定义aop拦截dao层方法:
 */
@Component
@Aspect
@Slf4j
public class DataSourceAspect {


    private static final String[] queryStrs = {"query", "select", "get"};

    /**
     * 定义切入点，切入点为com.angla.demo.dao下的所有方法
     */
    @Pointcut("execution(* com.angla.demo.dao.*.*(..))")
    public void executeSql() {
    }

    /**
     * 前置通知：在连接点之前执行的通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("executeSql()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String mName = methodSignature.getMethod().getName();
        log.info("拦截sql方法:{}", mName);
        DataSourceContextHolder.setDatabaseType(DataSourceTypeEnum.DATA_SOURCE_MASTER);
        for (String name : queryStrs) {
            if (mName.startsWith(name)) {
                log.info("查询语句，设置数据源为slave");
                DataSourceContextHolder.setDatabaseType(DataSourceTypeEnum.DATA_SOURCE_SLAVE);
                break;
            }
        }
        log.info("当前数据源:{}",DataSourceContextHolder.getDatabaseType().getName());
    }
}
