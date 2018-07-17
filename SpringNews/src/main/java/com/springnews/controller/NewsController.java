package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.springnews.entity.NewsEntity;
import com.springnews.service.NewsEntityService;
import com.springnews.service.NewsService;
import com.springnews.service.RegionService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsEntityService newsEntityService;

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

    @PostMapping(path = "/test-mongodb")
    public String testDB(HttpServletRequest request) {
        System.out.println("test");
        int pid = 2;
        String content = "{}";
        NewsEntity news = new NewsEntity();
        news.setPid(pid);
        DBObject bson = (DBObject) JSON.parse(content);
        news.setContent(bson);
        newsEntityService.saveNewsEntity(news);

        String token = request.getHeader("Authorization");
        String username = getUserName(token);
        System.out.println(username);

        return null;
    }

    private String getUserName(String token){

        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
            return user;
        } else {
            return null;
        }
    }
}
