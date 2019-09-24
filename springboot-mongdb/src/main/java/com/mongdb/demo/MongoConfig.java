package com.mongdb.demo;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author liudongting
 * @date 2019/7/23 13:30
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    private String mongoHost = "127.0.0.1";
    private int mongoPort = 27017;
    private String dbName = "snow";

    private static final String MONGO_BASE_PACKAGE = "com.mongdb.demo";

    @Autowired
    private ApplicationContext appContext;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public MongoClient mongo() throws Exception {
        MongoClient mongoClient = new MongoClient(mongoHost, mongoPort);
        return mongoClient;
    }

    @Override
    protected String getMappingBasePackage() {
        return MONGO_BASE_PACKAGE;
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }
}