package com.mongdb.demo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 访问记录
 * @author liudongting
 */
@Document(collection = "PublishVisit")
public class PublishVisit {
    @Id
    private ObjectId id;

    /**
     * 应用ID
     */
    @Indexed
    private Long appId;

    /**
     * 每日访问量
     */
    private Long visitCount;

    /**
     * 创建日期
     */
    @Indexed
    private Date createdDate;


    private String name;

    public PublishVisit(Long appId, Long visitCount) {
        this.appId = appId;
        this.visitCount = visitCount;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
