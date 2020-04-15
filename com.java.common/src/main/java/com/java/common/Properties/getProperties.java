package com.java.common.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class getProperties {

    public void get(){

        //获取inputStream流 读取redis.properties配置文件
        InputStream inputStream = getProperties.class.getClassLoader().
                getResourceAsStream("redis.properties");
        //Properties集合获取配置文件中的配置属性
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
         String aa =  properties.getProperty("redis.maxTotal");
    }

}
