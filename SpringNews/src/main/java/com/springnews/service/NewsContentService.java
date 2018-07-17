package com.springnews.service;

import com.springnews.entity.NewsContent;
import com.springnews.entity.NewsContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsContentService {
    @Autowired
    private NewsContentRepository newsContentRepository;

//    @Transactional
//    public boolean publishNews(int pid, String content){
//        NewsContent newsContent = new NewsContent();
//        newsContent.setPid(pid);newsContent.setContent(content);newsContent.setWords(content.length());
//        newsContentRepository.save(newsContent);
//        return true;
//    }

    public NewsContent findByPid(int pid){
        return newsContentRepository.findByPid(pid);
    }
}
