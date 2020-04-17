package com.study.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/1 10:43
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class JedisConfig {

    private String host;
    private int port;
    private String password;
    private int jedisPoolMaxIdle;
    private long jedisPoolMaxWait;
    private int jedisPoolMinIdle;
    private int timeout;

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedisPoolMaxIdle);
        jedisPoolConfig.setMaxWaitMillis(jedisPoolMaxWait);
        jedisPoolConfig.setMinIdle(jedisPoolMinIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }
}
