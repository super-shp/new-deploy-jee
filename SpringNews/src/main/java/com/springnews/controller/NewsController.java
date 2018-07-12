package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springnews.entity.News;
import com.springnews.entity.NewsRepository;
import com.springnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping(path = "/article-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNewsList(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("filter") String filter){
        newsService.setTopByPid(2);
        JsonObject newsJson = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        newsJson.addProperty("errorCode", 200);
        newsJson.addProperty("msg", "OK");
        newsJson.add("data", newsService.getNewsList(currentPage, pageSize, filter));
        Gson gson = new Gson();
        return gson.toJson(newsJson);
    }
}
