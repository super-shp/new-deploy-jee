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

    @PostMapping(path = "/article-list")
    public String getNewsList(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("filter") String filter){
        JsonObject newsJson = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        newsJson.addProperty("errorCode", 200);
        newsJson.addProperty("msg", "OK");
        JsonObject newsList = newsService.getNewsList(currentPage, pageSize, filter);
        if(newsList!=null){
            newsJson.add("data", newsList);
        }
        else{
            newsJson.addProperty("data", "null");
        }
        Gson gson = new Gson();
        return gson.toJson(newsJson);
    }

    @PostMapping(path = "/option")
    public void NewsOperate(@RequestParam("pid") int pid, @RequestParam("op") String op){
        switch (op){
            case "set-top":
                newsService.setTopByPid(pid);
                break;
            case "callback":
                newsService.callBackByPid(pid);
                break;
            case "delete":
                newsService.deleteByPid(pid);
                break;
        }
    }
}
