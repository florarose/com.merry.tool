package com.merry.mysql.rw.config;



import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/11 11:47
 * @Description: mm
 */
public class MybatisConfiguration {


    @Autowired
    private Environment env;
    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean
    public DataSource masterDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.mastersource.driver-class-name"));
        props.put("url", env.getProperty("spring.mastersource.url"));
        props.put("username", env.getProperty("spring.mastersource.username"));
        props.put("password", env.getProperty("spring.mastersource.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource slaveDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.slavesource1.driver-class-name"));
        props.put("url", env.getProperty("spring.slavesource1.url"));
        props.put("username", env.getProperty("spring.slavesource1.username"));
        props.put("password", env.getProperty("spring.slavesource1.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }
    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    @DependsOn({"masterDataSource","slaveDataSource"})
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.DATA_SOURCE_MASTER.getName(), masterDataSource);
        targetDataSources.put(DataSourceTypeEnum.DATA_SOURCE_SLAVE.getName(),slaveDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(slaveDataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(Objects.requireNonNull(env.getProperty(
                        "mybatis.mapperLocations"))));
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}