package com.redis.study;

import redis.clients.jedis.Jedis;

import java.util.Date;

public class test {



    public static void main(String[] args) {
//        Jedis jedis = new Jedis("47.100.2.226", 6379);
        Jedis jedis = RedisConfig.getJedis();
        jedis.auth("123456");
       if(jedis.isConnected()){
           System.out.println("ee"+jedis.ping());
//            logger.info("redis conneted : {} "+jedis.ping());
//            Date d = new Date();
//            String key = userId+"_"+MyDateUtils.DatetoString(d);
//            String result = jedis.set(key,formId);
//            result = jedis.set(key,formId,"nx","ex",redisTimeout);
            jedis.close();
        }else {
//            logger.error("redis conneted faile : {} ");
           System.out.println("redis conneted faile");
        }
    }


}
