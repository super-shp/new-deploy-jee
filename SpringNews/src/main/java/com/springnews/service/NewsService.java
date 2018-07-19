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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsContentService newsContentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    public NewsList getNewsList(Integer currentPage, Integer pageSize, String filter, String username) throws Exception{
        currentPage = currentPage - 1;
        pageSize = pageSize - 1;
        if(currentPage < 0 || pageSize < 0){
            throw new NewsException(ResultEnum.PARAM_ERROR);
        }
//        if (allNews.size() <= (currentPage-1)*pageSize){
//            throw new NewsException(ResultEnum.QUERY_ERROR);
//        }

        MyUser user = userService.findByUsername(username);
        NewsList newsList = new NewsList();
        newsList.setTotal(newsRepository.findAll().size());
        newsList.setOffset(currentPage*(pageSize+1));
        if(currentPage == 0){
            News topNews = newsRepository.findByStatus(2).get(0);
            if(topNews!=null) {
                newsList.getArticleList().add(topNews);
            }
        }

        if(user == null){
            user = new MyUser();
            user.setRoot(1);
        }

        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        if(user.getRoot() == 0) {
            Page<News> pages = newsRepository.findAll(pageRequest);
            Iterator<News> i = pages.iterator();
            if (!i.hasNext()) {
                throw new NewsException(ResultEnum.QUERY_ERROR);
            }
            while (i.hasNext()) {
                newsList.getArticleList().add(i.next());
            }
        }
        else{
            Page<News> pages = newsRepository.findByStatus(1, pageRequest);
            Iterator<News> i = pages.iterator();
            if (!i.hasNext()) {
                throw new NewsException(ResultEnum.QUERY_ERROR);
            }
            while (i.hasNext()) {
                newsList.getArticleList().add(i.next());
            }
        }
        newsList.getArticleList().sort(Comparator.naturalOrder());
        if(currentPage == 0){
            News topNews = newsRepository.findByStatus(2).get(0);
            if(topNews!=null) {
                newsList.getArticleList().add(0, topNews);
            }
        }

//        for(int i = (currentPage-1)*pageSize; i < currentPage*pageSize; i++){
//            if(i == allNews.size()){
//                break;
//            }
//            News temp = allNews.get(i);
//            newsList.getArticleList().add(temp);
//        }
        return newsList;
    }

    @Transactional
    public int publishNews(String title, int cid, String username, String cover, String intro) throws Exception{
        MyUser user = userService.findByUsername(username);
        UserInfo userInfo = userInfoService.findByUid(user.getUid());
        if(user.getRoot() != 0){
            throw new UserException(ResultEnum.USER_AUTH_ERROR);
        }
        News news = new News();
        news.setTitle(title);news.setCid(cid);news.setCover(cover);
        news.setCreated_time(new Date()); news.setUpdated_time(new Date());
        news.setUid(user.getUid());news.setAuthor(user.getAuthor());news.setFigure(userInfo.getFigure());
        news.setStatus(1);news.setVisited(0);news.setLiked(0);
        news.setIntro(intro);
        News temp = newsRepository.save(news);
        return news.getPid();
    }

    public Article getNewsByPid(int pid) throws Exception{
        TimeFormatTransform timeFormatTransform = new TimeFormatTransform();
        News news = newsRepository.findByPid(pid);
        NewsContent newsContent = newsContentService.findNewsByPid(pid);
        System.out.println(news);
        System.out.println(newsContent);
        if(news==null || newsContent==null){
            throw new NewsException(ResultEnum.QUERY_ERROR);
        }
        String uid = news.getUid();
        MyUser user = userService.findByUid(uid);

        Article article = new Article();

        article.setPid(news.getPid());article.setTitle(news.getTitle());
        article.setUid(news.getUid());article.setWords(newsContent.getWords());
        article.setUpdated_time(timeFormatTransform.dateToTimeStamp(news.getUpdated_time()));
        article.setCid(news.getCid());article.setVisited(news.getVisited());
        article.setLiked(news.getLiked());article.setCover(news.getCover());

        article.setContent(newsContent.getContent());

        if(user != null){
            article.setAuthor(user.getAuthor());
        }
        else{
            article.setAuthor("未查询到该文章作者");
        }
        return article;
    }

    @Transactional
    public boolean setTopByPid(int pid) throws Exception{
        News temp = newsRepository.findByPid(pid);
        if(temp == null){
            throw new NewsException(ResultEnum.QUERY_ERROR);
            //return false;
        }
        else{
            List<News> topNews = newsRepository.findByStatus(2);
            for(int i = 0; i < topNews.size(); i++){
                News top = topNews.get(i);
                top.setStatus(1);
                newsRepository.save(top);
            }
            temp.setStatus(2);
            newsRepository.save(temp);
            return true;
        }
    }

    @Transactional
    public boolean callBackByPid(int pid) throws Exception{
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
    public void deleteByPid(int pid) throws Exception{
        newsRepository.deleteByPid(pid);
    }

    public boolean newsOperate(int pid, String op) throws Exception{
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
