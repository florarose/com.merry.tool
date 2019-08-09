package com.mongdb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/23 13:33
 */
@Repository
public class publishDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(PublishVisit publishVisit) {
        this.mongoTemplate.insert(publishVisit);
    }
    public void update(PublishVisit publishVisit) {
        this.mongoTemplate.save(publishVisit);
    }

    public List<PublishVisit> findAll(Class<PublishVisit> entityClass){
       return this.mongoTemplate.findAll(entityClass);
    }
}
