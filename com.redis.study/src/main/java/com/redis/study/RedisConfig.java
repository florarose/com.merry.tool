package com.redis.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;

public class RedisConfig {

    @Value("${spring.redis.host}")
    private static String redisHost;

    @Value("${spring.redis.port}")
    private  static Integer redisPort;

    @Value("${spring.redis.password}")
    private  static String password;

    @Value("${spring.redis.timeout}")
    private  static Integer redisTimeout;

    @Value("${spring.redis.pool.min-idle}")
    private  static Integer minIdle;

    @Value("${spring.redis.pool.max-idle}")
    private static  Integer maxIdle;

    @Value("${spring.redis.pool.max-active}")
    private static  Integer maxActive;

    //声明静态的JedisPool属性
    private static JedisPool jedisPool = null;
    //静态块
    static {
        //创建JedisPoolConfig连接池配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(1);
        //创建redis连接池，把配置对象给她
        String hh = "47.100.2.226";
        Integer mm = 6379;
        redisHost = hh;
        redisPort = mm;
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    }
    /**
     * 得到redis操作资源对象方法
     * @return Jedis操作资源对象
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
