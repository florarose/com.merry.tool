package com.redis.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/15 11:23
 * @version: 1.0.0
 */

@Slf4j
@Component
public class RedisUtils {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String runLuaScript(String luaFileName, List<String> keyList) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/" + luaFileName)));
        redisScript.setResultType(String.class);
        String result = "";
        String argsone = "none";
        //logger.error("开始执行lua");
        try {
            result = stringRedisTemplate.execute(redisScript, keyList, argsone);
        } catch (Exception e) {
            log.error("秒杀失败", e);
        }
        return result;
    }

}
