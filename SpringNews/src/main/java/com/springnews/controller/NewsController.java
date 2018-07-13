package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springnews.service.NewsService;
import com.springnews.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private RegionService regionService;

    private ResponseJson responseJson = new ResponseJson();

    @PostMapping(path = "/article-list")
    public String getNewsList(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("filter") String filter){
        JsonObject newsList = newsService.getNewsList(currentPage, pageSize, filter);
        if(newsList!=null){
            return responseJson.responseJson(200, "OK", newsList);
        }
        else{
            return responseJson.responseJson(200, "OK", null);
        }
    }

    @PostMapping(path = "/option")
    public String NewsOperate(@RequestParam("pid") int pid, @RequestParam("op") String op){
        switch (op){
            case "set-top":
                if(newsService.setTopByPid(pid)){
                    return responseJson.responseJson(200, "OK", null);
                }
                else {
                    return responseJson.responseJson(100, "OK", null);
                }
            case "callback":
                if(newsService.callBackByPid(pid)){
                    return responseJson.responseJson(200, "OK", null);
                }
                else {
                    return responseJson.responseJson(100, "OK", null);
                }
            case "delete":
                newsService.deleteByPid(pid);
                return responseJson.responseJson(200, "OK", null);
            default:
                return responseJson.responseJson(100, "OK", null);
        }
    }

    @PostMapping(path = "/get-column")
    public String getColumnList(){
        JsonObject regionList = regionService.getRegionList();
        if(regionList!=null){
            return responseJson.responseJson(200, "OK", regionList);
        }
        else{
            return responseJson.responseJson(200, "OK", null);
        }
    }
}
