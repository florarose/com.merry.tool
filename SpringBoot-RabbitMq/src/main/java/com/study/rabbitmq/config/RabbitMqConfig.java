//package com.study.rabbitmq.config;
//
//
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/1 10:43
// */
//public class RabbitMqConfig {
//
//    public static Connection getConnection() throws Exception {
//        //定义连接工厂
//        ConnectionFactory factory = new ConnectionFactory();
//        //设置服务地址
//        factory.setHost("localhost");
//        //端口
//        factory.setPort(5672);
//        //设置账号信息，用户名、密码、vhost
//        factory.setVirtualHost("testhost");
//        factory.setUsername("admin");
//        factory.setPassword("admin");
//        // 通过工程获取连接
//        Connection connection = factory.newConnection();
//        return connection;
//    }
//
//}
