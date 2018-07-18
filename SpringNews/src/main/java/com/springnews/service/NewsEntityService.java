package com.springnews.service;

import com.springnews.entity.NewsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        System.out.println(" 保存至 mongodb");

        mongoTemplate.save(news);
    }

    /**
     * 根据 pid 查找文章
     * @param pid
     * @return
     */
    public NewsEntity findNewsByPid(int pid) {
        Query query = new Query(Criteria.where("userName").is(pid));
        NewsEntity news =  mongoTemplate.findOne(query , NewsEntity.class);
        return news;
    }

    /**
     * 更新对象
     * @param news
     */
    public void updateNewsByPid(NewsEntity news) {
        Query query=new Query(Criteria.where("pid").is(news.getPid()));
        Update update= new Update().set("content", news.getContent());
        // 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,NewsEntity.class);
        // 更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     * @param pid
     */
    public void deleteNewsByPid(int pid) {
        Query query=new Query(Criteria.where("pid").is(pid));
        mongoTemplate.remove(query,NewsEntity.class);
    }
}