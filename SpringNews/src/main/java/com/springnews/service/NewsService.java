package com.springnews.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.springnews.entity.*;
import com.springnews.enums.ResultEnum;
import com.springnews.exception.NewsException;
import com.springnews.exception.UserException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsEntityService newsEntityService;

    @Autowired
    private NewsContentService newsContentService;

    @Autowired
    private UserService userService;

    public NewsList getNewsList(int currentPage, int pageSize, String filter) throws NewsException{
        if(currentPage <= 0 || pageSize <= 0){
            throw new NewsException(ResultEnum.PARAM_ERROR);
        }
        List<News> allNews = newsRepository.findAll();
        if (allNews.size() <= (currentPage-1)*pageSize){
            throw new NewsException(ResultEnum.QUERY_ERROR);
        }
        NewsList newsList = new NewsList();
        newsList.setTotal(allNews.size());
        newsList.setOffset((currentPage-1)*pageSize);
        for(int i = (currentPage-1)*pageSize; i < currentPage*pageSize; i++){
            if(i == allNews.size()){
                break;
            }
            News temp = allNews.get(i);
            newsList.getArticleList().add(temp);
        }
        return newsList;
    }

    @Transactional
    public int publishNews(String title, int cid, String username, String cover, String content) throws Exception{
        MyUser user = userService.findByUsername(username);
        if(user.getRoot() != 0){
            throw new UserException(ResultEnum.USER_AUTH_ERROR);
        }
        News news = new News();
        news.setTitle(title);news.setCid(cid);news.setCover(cover);
        news.setUid(user.getUid());news.setAuthor(user.getAuthor());
        news.setStatus(1);news.setVisited(0);news.setLiked(0);
        News temp = newsRepository.save(news);
        newsContentService.publishNews(temp.getPid(), content);
        return news.getPid();
    }

//    public JsonObject getNews(int pid){
//        TimeFormatTransform timeFormatTransform = new TimeFormatTransform();
//        News news = newsRepository.findByPid(pid);
//        NewsContent newsContent = newsContentService.findByPid(pid);
//        String uid = news.getUid();
//        MyUser user = userService.findByUid(uid);
//
//        JsonObject newsJson = new JsonObject();
//        newsJson.addProperty("pid", news.getPid());
//        newsJson.addProperty("uid", news.getUid());
//        newsJson.addProperty("liked", news.getLiked());
//        newsJson.addProperty("cover", news.getCover());
//        newsJson.addProperty("title", news.getTitle());
//        newsJson.addProperty("author", user.getAuthor());
//        newsJson.addProperty("intro", news.getIntro());
//        newsJson.addProperty("region_name", news.getRegion());
//        newsJson.addProperty("visited", news.getVisited());
//        newsJson.addProperty("cid", news.getCid());
//        newsJson.addProperty("updated_time", timeFormatTransform.dateToTimeStamp(news.getUpdated_time()));
//        newsJson.addProperty("content", newsContent.getContent());
//        newsJson.addProperty("words", newsContent.getWords());
//        return newsJson;
//    }

    @Transactional
    public boolean setTopByPid(int pid) throws NewsException{
        News temp = newsRepository.findByPid(pid);
        if(temp == null){
            throw new NewsException(ResultEnum.QUERY_ERROR);
            //return false;
        }
        else{
            temp.setStatus(2);
            newsRepository.save(temp);
            return true;
        }
    }

    @Transactional
    public boolean callBackByPid(int pid) throws NewsException{
        News temp = newsRepository.findByPid(pid);
        if(temp == null){
            throw new NewsException(ResultEnum.QUERY_ERROR);
            //return false;
        }
        else{
            temp.setStatus(0);
            newsRepository.save(temp);
            return true;
        }
    }

    @Transactional
    public void deleteByPid(int pid){
        newsRepository.deleteByPid(pid);
    }

    public boolean newsOperate(int pid, String op) throws NewsException{
        switch (op){
            case "set-top":
                if(setTopByPid(pid)){
                    return true;
                }
            case "callback":
                if(callBackByPid(pid)){
                    return true;
                }
            case "delete":
                deleteByPid(pid);
                return true;
            default:
                throw new NewsException(ResultEnum.PARAM_ERROR);
        }
    }


}
