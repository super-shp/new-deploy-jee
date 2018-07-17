package com.springnews.controller;


import com.springnews.controller.postinfo.PostNewsListJson;
import com.springnews.controller.postinfo.PostNewsOptionJson;
import com.springnews.entity.*;
import com.springnews.enums.ResultEnum;
import com.springnews.exception.NewsException;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.springnews.entity.NewsEntity;
import com.springnews.service.NewsEntityService;
import com.springnews.service.NewsService;
import com.springnews.service.RegionService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsEntityService newsEntityService;

    @Autowired
    private RegionService regionService;

    @PostMapping(path = "/article-list")
    public UnifyResponse<NewsList> getNewsList(@RequestBody PostNewsListJson postNewsListJson) throws NewsException{
        System.out.println(postNewsListJson.getCurrentPage());
        NewsList newsList = newsService.getNewsList(postNewsListJson.getCurrentPage(), postNewsListJson.getPageSize(), postNewsListJson.getFilter());
        return ResultUtil.successs(ResultEnum.OK, newsList);

    }

    @PostMapping(path = "/option")
    public UnifyResponse NewsOperate(@RequestBody PostNewsOptionJson postNewsOptionJson) throws NewsException{
        newsService.newsOperate(postNewsOptionJson.getPid(), postNewsOptionJson.getOp());
        return ResultUtil.successs(ResultEnum.OK);
    }

    @PostMapping(path = "/get-column")
    public UnifyResponse<RegionList> getColumnList() throws Exception{
        RegionList regionList = regionService.getRegionList();
        return ResultUtil.successs(ResultEnum.OK, regionList);
    }

    @PostMapping(path = "post-article")
    public UnifyResponse publishNews(@RequestHeader("Authorization") String token, @RequestBody String requestBody) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        String title = jsonObject.getString("title");
        int cid = jsonObject.getInt("cid");
        String username = getUserName(token);
        String cover = jsonObject.getString("cover");
        String contents = jsonObject.getString("content");

        newsService.publishNews(title, cid, username, cover, contents);
        return ResultUtil.successs(ResultEnum.OK);
    }

    @PostMapping(path = "/test-mongodb")
    public UnifyResponse testDB(@RequestHeader("Authorization") String token, @RequestBody String requestBody) {
        System.out.println("test");
        int pid = 2;
        String content = "{}";
        NewsEntity news = new NewsEntity();
        news.setPid(pid);
        DBObject bson = (DBObject) JSON.parse(content);
        news.setContent(bson);
        newsEntityService.saveNewsEntity(news);

        String username = getUserName(token);
        System.out.println(username);

        System.out.println(requestBody.getClass());

        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        int uid = jsonObject.getInt("uid");
        JSONObject contents = jsonObject.getJSONObject("content");
        JSONArray arr = contents.getJSONArray("arr");
        System.out.println(arr);


        return ResultUtil.successs(ResultEnum.OK);

//        if (true){
//            return responseJson.responseJson(ResponseCode.SUCCESS,null);
//        }else if(false){
//            return responseJson.responseJson(ResponseCode.UNKNOW_ERROR, null);
//        }else {
//            // 或者可以自由定义返回值，但通常不建议这么做
//            return responseJson.responseJson(100, "OK", null);
//        }

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
