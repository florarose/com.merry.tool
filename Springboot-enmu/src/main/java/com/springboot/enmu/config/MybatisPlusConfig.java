package com.springboot.enmu.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.incrementer.PostgreKeyGenerator;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


/**
 * @author liudongting
 * @date 2019/9/10 12:03
 */
@Configuration
@MapperScan(basePackages = "com.springboot.enmu.dao", sqlSessionFactoryRef = "mybatisSqlSessionFactoryBean")
public class MybatisPlusConfig {

  @Autowired
  @Qualifier("dataSource")
  private DataSource dataSource;

//  @Autowired
//  private MybatisProperties properties;

  /**

   * mybatis-plus分页插件<br>

   * 文档：http://mp.baomidou.com<br>

   */
  private PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor pagination = new PaginationInterceptor();
    pagination.setOverflowCurrent(true);
    pagination.setDialectClazz("com.baomidou.mybatisplus.plugins.pagination.dialects.MysqlDialect");
    return pagination;
  }

  private OptimisticLockerInterceptor optimisticLockerInterceptor() {
    OptimisticLockerInterceptor optimisticLock = new OptimisticLockerInterceptor();
    return optimisticLock;
  }

  @Bean(name = "transactionManager")
  @Qualifier("transactionManager")
  public PlatformTransactionManager masterTransactionManager(){
    return new DataSourceTransactionManager(dataSource);
  }

  @Autowired(required = false)
  private DatabaseIdProvider databaseIdProvider;

  /*
   * @Bean public PaginationInterceptor paginationInterceptor() { PaginationInterceptor interceptor
   * = new PaginationInterceptor(); interceptor.setDialectType("postgresql"); return interceptor; }
   */

  @Bean(name = "mybatisSqlSessionFactoryBean")
  @Primary
  public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws Exception {
    MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setVfs(SpringBootVFS.class);
//    if (StringUtil.hasText(this.properties.getConfigLocation())) {
//      factoryBean
//          .setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//    }
//    factoryBean.setConfiguration(properties.getConfiguration());


    factoryBean.setPlugins(new Interceptor[]{paginationInterceptor(), optimisticLockerInterceptor()});
    GlobalConfiguration globalConfiguration = new GlobalConfiguration();
    globalConfiguration.setKeyGenerator(new PostgreKeyGenerator());
    globalConfiguration.setDbType(DBType.MYSQL.getDb());
    globalConfiguration.setIdentifierQuote(DBType.MYSQL.getQuote());
    factoryBean.setGlobalConfig(globalConfiguration);
    MybatisConfiguration mc = new MybatisConfiguration();
    mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
    factoryBean.setConfiguration(mc);
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//    // 取得类型转换注册器
    TypeHandlerRegistry typeHandlerRegistry = factoryBean.getObject().getConfiguration().getTypeHandlerRegistry();
//    // 注册默认枚举转换器
    typeHandlerRegistry.register(AutoEnumTypeHandler.class);
    try {
      Resource[] resources = resourcePatternResolver.getResources( ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "mapper/*Mapper.xml");
      factoryBean.setMapperLocations(resources);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (this.databaseIdProvider != null) {
      factoryBean.setDatabaseIdProvider(this.databaseIdProvider);
    }
    return factoryBean;
  }
}
