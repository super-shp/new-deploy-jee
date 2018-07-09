package com.springnews.controller;

import com.springnews.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StartController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsContentRepository newsContentRepository;

    private TimeFormatTransform timeFormatTransform;

    @GetMapping(value = "/start")
    public List<NewsContent> test(){
        //timeFormatTransform = new TimeFormatTransform();
        //timeFormatTransform.dateToTimeStamp(userRepository.findAll().get(0).getCreated_time());
//        NewsContent n = new NewsContent();
//        n.setPid(1);n.setUid(1);n.setTitle("title");n.setWords(0);n.setAuthor("author");n.setCreated_time(new Date());
//        n.setUpdated_time(new Date());n.setStatus(0);n.setColumn("c");n.setVisited(0);n.setLike(0);
//        n.setCover("null");
//        newsContentRepository.save(n);
        //return userRepository.findAll();
        return newsContentRepository.findAll();
    }

    @GetMapping(value = "/users")
    public List<MyUser> queryUser(){
        return userRepository.findAll();
    }

}
