//package com.mongdb;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoClientURI;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
///**
// * @author liudongting
// * @date 2019/7/22 14:39
// */
//@Configuration
//@EnableMongoAuditing
//@EnableMongoRepositories("com.mongdb")
//public class WcfMongoAutoConfiguration {
//
//
//    /**
//     * 功能描述: 根据自己创建的工厂初始化一个template
//     *
//     * @param
//     * @return:org.springframework.data.mongodb.core.MongoTemplate
//     * @since: v1.0
//     * @author liudongting
//     * @date 2019/7/22 14:39
//     */
//
//    @Bean
//    public MongoTemplate template() throws  Exception{
//        MongoClientOptions.Builder mongoBuilder = new MongoClientOptions.Builder();
//        mongoBuilder.maxWaitTime(1000*60*3);
//        mongoBuilder.connectTimeout(60*1000*3);    //与数据库建立连接的timeout设置为1分钟
//        mongoBuilder.minConnectionsPerHost(1);
//        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://47.111.160.211:27017/snow",mongoBuilder);
//        //spring.data.mongodb.uri=mongodb://root:root@127.0.0.1:27017/test
//        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
////        List<PublishVisit> aa=    mongoTemplate.findAll(PublishVisit.class);
////        PublishVisit ddd =  mongoTemplate.findById("5d357efcc213e933acefc60c",PublishVisit.class);
//        return mongoTemplate;
//    }
//
//
//
//}
