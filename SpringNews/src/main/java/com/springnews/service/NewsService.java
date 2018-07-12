package com.springnews.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.springnews.entity.News;
import com.springnews.entity.NewsRepository;
import com.springnews.entity.TimeFormatTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public JsonObject getNewsList(int currentPage, int pageSize, String filter){
        if(currentPage <= 0 || pageSize <= 0){
            return null;
        }
        List<News> newsList = newsRepository.findAll();
        if (newsList.size() <= (currentPage-1)*pageSize){
            return null;
        }
        TimeFormatTransform timeFormatTransform = new TimeFormatTransform();


        JsonObject newsJson = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        newsJson.addProperty("total", newsList.size());
        newsJson.addProperty("offset", (currentPage-1)*pageSize);

        JsonArray newsArray = new JsonArray();
        //System.out.println(newsJson);
        for(int i = (currentPage-1)*pageSize; i < currentPage*pageSize; i++){
            if(i == newsList.size()){
                break;
            }
            JsonObject news = new JsonObject();
            news.addProperty("pid", newsList.get(i).getPid());
            news.addProperty("uid", newsList.get(i).getUid());
            news.addProperty("figure", newsList.get(i).getFigure());
            news.addProperty("liked", newsList.get(i).getLiked());
            news.addProperty("cover", newsList.get(i).getCover());
            news.addProperty("title", newsList.get(i).getTitle());
            news.addProperty("author", newsList.get(i).getAuthor());
            news.addProperty("intro", newsList.get(i).getIntro());
            news.addProperty("region_name", newsList.get(i).getRegion());
            news.addProperty("visited", newsList.get(i).getVisited());
            news.addProperty("cid", newsList.get(i).getCid());
            news.addProperty("updated_time", timeFormatTransform.dateToTimeStamp(newsList.get(i).getUpdated_time()));
            newsArray.add(news);
        }
        newsJson.add("articleList", newsArray);
        return newsJson;
    }

    @Transactional
    public boolean setTopByPid(int pid){
        News temp = newsRepository.findByPid(pid);
        if(temp == null){
            return false;
        }
        else{
            temp.setStatus(2);
            newsRepository.save(temp);
            return true;
        }
    }

    @Transactional
    public boolean callBackByPid(int pid){
        News temp = newsRepository.findByPid(pid);
        if(temp == null){
            return false;
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


}
