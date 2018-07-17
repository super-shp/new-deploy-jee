package com.springnews.service;

import com.springnews.entity.NewsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewsEntityService {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param news
     */
    public void saveNewsEntity(NewsEntity news) {
        System.out.println("保存至mongodb");

        mongoTemplate.save(news);
    }
}
