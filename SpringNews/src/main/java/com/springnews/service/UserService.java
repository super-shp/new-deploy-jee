package com.springnews.service;

import com.springnews.entity.MyUser;
import com.springnews.entity.TimeFormatTransform;
import com.springnews.entity.UserInfo;
import com.springnews.entity.UserRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoService userInfoRepository;

    public List<MyUser> getUserList(){
        return userRepository.findAll();
    }

    public MyUser findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public MyUser findByUid(String uid){
        return userRepository.findByUid(uid);
    }

    @Transactional
    public boolean addUser(MyUser user){
        if(findByUsername(user.getUsername())==null){
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional
    public boolean deleteUserByUsername(String username){
        MyUser user = findByUsername(username);
        if(user!=null){
            userRepository.delete(user);
            return true;
        }
        else {
            return false;
        }
    }

    //传入一个User对象作为参数, 查找数据库中有没有与参数拥有相同用户名的用户, 如果有, 将其信息更新为与传入参数相同
    @Transactional
    public JSONObject getUserInfo(MyUser user){
        MyUser temp = findByUsername(user.getUsername());
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
        return infoJson;
    }

    //传入一个User对象作为参数, 查找数据库中有没有与参数拥有相同用户名的用户, 如果有, 将其信息更新为与传入参数相同
    @Transactional
    public boolean updateUser(MyUser user){
        MyUser temp = findByUsername(user.getUsername());
        if(temp == null){
            return false;
        }
        else {
            user.setId(temp.getId());
            userRepository.save(user);
            return true;
        }
    }
}
