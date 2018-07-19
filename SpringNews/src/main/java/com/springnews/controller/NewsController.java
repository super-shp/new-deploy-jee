package com.springnews.controller;


import com.springnews.controller.postinfo.PostNewsListJson;
import com.springnews.controller.postinfo.PostNewsOptionJson;
import com.springnews.entity.*;
import com.springnews.enums.ResultEnum;
import com.springnews.service.NewsContentService;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import net.sf.json.JSONObject;
import com.springnews.service.NewsService;
import com.springnews.service.RegionService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/article")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsContentService newsContentService;

    @Autowired
    private RegionService regionService;

    @PostMapping(path = "/article-list")
    public UnifyResponse<NewsList> getNewsList(HttpServletRequest req, @RequestBody PostNewsListJson postNewsListJson) throws Exception{
        String token = req.getHeader("Authorization");
        String username = getUserName(token);
        NewsList newsList = newsService.getNewsList(postNewsListJson.getCurrentPage(), postNewsListJson.getPageSize(), postNewsListJson.getFilter(), username);
        return ResultUtil.successs(ResultEnum.OK, newsList);

    }

    @PostMapping(path = "/option")
    public UnifyResponse NewsOperate(@RequestBody PostNewsOptionJson postNewsOptionJson) throws Exception{
        newsService.newsOperate(postNewsOptionJson.getPid(), postNewsOptionJson.getOp());
        return ResultUtil.successs(ResultEnum.OK);
    }

    @PostMapping(path = "/get-column")
    public UnifyResponse<RegionList> getColumnList() throws Exception{
        RegionList regionList = regionService.getRegionList();
        return ResultUtil.successs(ResultEnum.OK, regionList);
    }

    @PostMapping(path = "/post-article")
    public UnifyResponse publishNews(HttpServletRequest req, HttpServletResponse res, @RequestBody String requestBody) throws Exception{
        String token = req.getHeader("Authorization");
        if (token == null){
            res.setStatus(403);
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        String username = getUserName(token);

        newsService.publishNews(username, jsonObject);
        return ResultUtil.successs(ResultEnum.OK);
    }

    @PostMapping(path = "/modify-article")
    public UnifyResponse modifyNews(HttpServletRequest req, HttpServletResponse res, @RequestBody String requestBody) throws Exception{
        String token = req.getHeader("Authorization");
        if (token == null){
            res.setStatus(403);
            return null;
        }
        String username = getUserName(token);
        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        // 修改mysql里的数据
        newsService.modifyNews(username, jsonObject);

        // 修改mongoDB里的数据
        JSONObject contents = jsonObject.getJSONObject("content");
        int words = jsonObject.getInt("words");
        int pid = jsonObject.getInt("pid");
        //DBObject bson = (DBObject) com.mongodb.util.JSON.parse(contents.toString());
        newsContentService.updateNewsByPid(pid, contents.toString(), words);
        return ResultUtil.successs(ResultEnum.OK);
    }

    @PostMapping(path = "/get-article")
    public UnifyResponse getArticle(HttpServletRequest req, @RequestBody String requestBody) throws Exception{
        String token = req.getHeader("Authorization");
        System.out.println(token);
        String username = getUserName(token);
        System.out.println(username);

        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        int pid = jsonObject.getInt("pid");
        return ResultUtil.successs(ResultEnum.OK, newsService.getNewsByPid(pid));
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
