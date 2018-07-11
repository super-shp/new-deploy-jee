package com.springnews.service;

import com.springnews.entity.MyUser;
import com.springnews.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
