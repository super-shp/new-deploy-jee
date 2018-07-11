package com.springnews.controller;

import com.springnews.entity.*;
import com.springnews.service.UserService;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StartController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsContentRepository newsContentRepository;

    private TimeFormatTransform timeFormatTransform;

    @GetMapping(value = "/start")
    public MyUser test(){
//        timeFormatTransform = new TimeFormatTransform();
//        timeFormatTransform.dateToTimeStamp(userRepository.findAll().get(0).getCreated_time());
//        NewsContent n = new NewsContent();
//        n.setPid(2);n.setUid(2);n.setTitle("title");n.setWords(0);n.setAuthor("author");n.setCreated_time(new Date());
//        n.setUpdated_time(new Date());n.setStatus(0);n.setColumn("c");n.setVisited(0);n.setLike(0);
//        n.setCover("null");
//        newsContentRepository.save(n);
        MyUser user = new MyUser();
        user.setAuthor("123451324");
        user.setCreated_time(new Date());
        user.setUpdated_time(new Date());
        user.setPassword("123");
        user.setUsername("0986789678");
        user.setRoot(0);
        user.setStatus(0);
        userService.deleteUserByUsername("123");
        return user;

        //return userRepository.findAll();
        //return newsContentRepository.findAll();
    }

    @GetMapping(value = "/users")
    public UnifyResponse<MyUser> queryUsers(){

        return ResultUtil.successs(userRepository.findAll());
    }

    @GetMapping(value = "/user/{id}")
    public UnifyResponse<MyUser> queryUser(@PathVariable("id") String uid){
        return ResultUtil.successs(userService.findByUid(uid));
    }

}
