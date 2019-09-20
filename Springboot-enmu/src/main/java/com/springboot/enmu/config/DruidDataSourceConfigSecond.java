package com.springboot.enmu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Druid的DataResource配置类
 * 凡是被Spring管理的类，实现接口 EnvironmentAware
 * 重写方法 setEnvironment 可以在工程启动时，
 * 获取到系统环境变量和application配置文件中的变量。
 *
 * 还有一种方式是采用注解的方式获取
 * @EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
 * @value("${变量的key值}") ：获取application配置文件中的变量。
 *
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfigSecond implements EnvironmentAware {

	private RelaxedPropertyResolver resolver;
	private static Logger logger = LoggerFactory.getLogger(DruidDataSourceConfigSecond.class);
    @Override
	public void setEnvironment(Environment env) {
		this.resolver = new RelaxedPropertyResolver(env, "spring.datasource.");
	}

	@Bean(name = "dataSource")
	@Qualifier("dataSource")
	public DataSource dataSource() {


		String url = resolver.getProperty("url");
		String username = resolver.getProperty("username");
		String password = resolver.getProperty("password");
		logger.error("配置 url 属性{} username====={}===password====={}=====", url, username, password);
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(url);
		datasource.setDriverClassName(resolver.getProperty("driver-class-name"));
		datasource.setUsername(resolver.getProperty("username"));
		datasource.setPassword(resolver.getProperty("password"));
		datasource.setInitialSize(Integer.valueOf(resolver.getProperty("initial-size")));
		datasource.setMinIdle(Integer.valueOf(resolver.getProperty("min-idle")));
//		datasource.setMaxWait(Long.valueOf(resolver.getProperty("max-wait")));
//		datasource.setMaxActive(Integer.valueOf(resolver.getProperty("max-active")));
//		datasource.setMinEvictableIdleTimeMillis(Long.valueOf(resolver.getProperty("min-evictable-idle-time-millis")));
		try {
			datasource.setFilters("stat,wall");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("e++ {} :", e);
		}
		return datasource;
	}



}
