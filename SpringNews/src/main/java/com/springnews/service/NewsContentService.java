package com.springnews.service;

import com.springnews.entity.NewsContent;
import com.springnews.entity.NewsContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsContentService {
    @Autowired
    private NewsContentRepository newsContentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public boolean publishNews(int pid, String content, int words){
        NewsContent newsContent = new NewsContent();
        newsContent.setPid(pid);
        newsContent.setContent(content);
        newsContent.setWords(words);
        newsContentRepository.save(newsContent);
        return true;
    }

    /**
     * 根据 pid 查找文章
     * @param pid
     * @return
     */
    public NewsContent findNewsByPid(int pid) {
        Query query = new Query(Criteria.where("pid").is(pid));
        NewsContent news = mongoTemplate.findOne(query , NewsContent.class);
        return news;
    }


    /**
     * 更新对象
     * @param news
     */
    public void updateNewsByPid(NewsContent news) {
        Query query = new Query(Criteria.where("pid").is(news.getPid()));
        Update update= new Update().set("content", news.getContent()).set("words", news.getWords());
        // 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,NewsContent.class);
        return;
    }

    /**
     * 删除对象
     * @param pid
     */
    public void deleteNewsByPid(int pid) {
        Query query=new Query(Criteria.where("pid").is(pid));
        mongoTemplate.remove(query,NewsContent.class);
    }
}
