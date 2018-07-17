package com.springnews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springnews.entity.MyUser;
import com.springnews.entity.UserRepository;
import com.springnews.enums.ResultEnum;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserRepository applicationUserRepository;
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getUsername());
        applicationUserRepository.save(user);
        return ResultUtil.successs(ResultEnum.OK, user);
    }

    @GetMapping("/getinfo")
    public UnifyResponse<MyUser> getInfo(@RequestHeader("Authorization") String token) {
        String username = getUserName(token);
        MyUser user = applicationUserRepository.findByUsername(username);
        return ResultUtil.successs(ResultEnum.OK, user);
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