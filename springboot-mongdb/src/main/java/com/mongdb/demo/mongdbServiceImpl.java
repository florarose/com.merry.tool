package com.mongdb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/22 14:46
 */
@Service
public class mongdbServiceImpl  {


    @Autowired
    publishDao publishDao;


    public List<PublishVisit> findAll(Class<PublishVisit> entityClass) {
        return publishDao.findAll(entityClass);
    }

    public  void save(PublishVisit publishVisit){
        save(publishVisit);
    }
}
