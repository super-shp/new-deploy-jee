package com.springnews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.springnews.entity.*;
import com.springnews.enums.ResultEnum;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UUIDUtils;
import com.springnews.utils.UnifyResponse;
import io.jsonwebtoken.Jwts;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository applicationUserRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository myUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = myUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/signup")
    public UnifyResponse<MyUser> signUp(@RequestBody MyUser user) {
        // 写入User表
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String uid = UUIDUtils.getUUID();
        user.setUid(uid);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        user.setCreated_time(time);
        user.setUpdated_time(time);

        applicationUserRepository.save(user);

        // 写入UserInfo表
        UserInfo userinfo = new UserInfo();
        userinfo.setLiked(0);
        userinfo.setUid(uid);
        userinfo.setFigure("");
        userinfo.setWords(0);
        userInfoRepository.save(userinfo);
        return ResultUtil.successs(ResultEnum.OK, user);
    }

    @GetMapping("/getinfo")
    public UnifyResponse<MyUser> getInfo(@RequestHeader("Authorization") String token) {
        String username = getUserName(token);
        MyUser user = applicationUserRepository.findByUsername(username);
        String uid = user.getUid();
        UserInfo userInfo = userInfoRepository.findByUid(uid);

        TimeFormatTransform transformer = new TimeFormatTransform();
        JSONObject infoJson = new JSONObject();
        infoJson.put("id", user.getId());
        infoJson.put("uid", user.getUid());
        infoJson.put("author", user.getAuthor());
        infoJson.put("likes", userInfo.getLiked());
        infoJson.put("words", userInfo.getWords());
        infoJson.put("status", user.getStatus());
        infoJson.put("created_time", transformer.dateToTimeStamp(user.getCreated_time()));
        infoJson.put("updated_time", transformer.dateToTimeStamp(user.getUpdated_time()));
        System.out.println(infoJson);
        return ResultUtil.successs(ResultEnum.OK, infoJson);
    }

    @PostMapping("/resetpassword")
    public void ResetPassword(@RequestParam("password") String password,
                              @RequestParam("new_password") String new_passwd){

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