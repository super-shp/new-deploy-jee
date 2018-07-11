package com.springnews.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

        JsonObject obj = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        obj.addProperty("total", newsList.size());
        obj.addProperty("offset", 1);

        JsonArray array = new JsonArray();

        for(int i = (currentPage-1)*pageSize; i < currentPage*pageSize; i++){
            JsonObject lan1 = new JsonObject();
            lan1.addProperty("pid", newsList.get(i).getPid());
            lan1.addProperty("uid", newsList.get(i).getUid());
            lan1.addProperty("figure", newsList.get(i).getFigure());
            lan1.addProperty("like", newsList.get(i).getLike());
            lan1.addProperty("cover", newsList.get(i).getCover());
            lan1.addProperty("title", newsList.get(i).getTitle());
            lan1.addProperty("author", newsList.get(i).getAuthor());
            lan1.addProperty("intro", newsList.get(i).getIntro());
            lan1.addProperty("column_name", newsList.get(i).getColumn());
            lan1.addProperty("visited", newsList.get(i).getVisited());
            lan1.addProperty("cid", newsList.get(i).getCid());
            lan1.addProperty("updated_time", timeFormatTransform.dateToTimeStamp(newsList.get(i).getUpdated_time()));
            array.add(lan1);
        }
        obj.add("articleList", array);
        return obj;
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
    public void deleteByPid(int pid){
        newsRepository.deleteById(pid);
    }


}
