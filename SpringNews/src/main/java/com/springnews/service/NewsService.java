package com.springnews.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.springnews.entity.*;
import com.springnews.enums.ResultEnum;
import com.springnews.exception.NewsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsContentService newsContentService;

    @Autowired
    private UserService userService;

    public NewsList getNewsList(int currentPage, int pageSize, String filter) throws NewsException{
        if(currentPage <= 0 || pageSize <= 0){
            System.out.println("异常之前");
            throw new NewsException(ResultEnum.OPTION_FAILED);
            //System.out.println("异常之后");
        }
        List<News> allNews = newsRepository.findAll();
        if (allNews.size() <= (currentPage-1)*pageSize){
            throw new NewsException(ResultEnum.OPTION_FAILED);
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
    public int publishNews(String title, int cid, String uid, String cover, String content){
        News news = new News();
        news.setTitle(title);news.setCid(cid);news.setUid(uid);news.setCover(cover);
        news.setStatus(1);news.setVisited(0);news.setLiked(0);news.setIntro(content.substring(0,100));
        news.setPid(news.getId());
        newsRepository.save(news);
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
            throw new NewsException(ResultEnum.OPTION_FAILED);
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
            throw new NewsException(ResultEnum.OPTION_FAILED);
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
                throw new NewsException(ResultEnum.OPTION_FAILED);
        }
    }


}
