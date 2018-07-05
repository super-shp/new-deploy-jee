package com.springnews.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class UserDao {
    @Autowired
    UserRepository userRepository;

    public List<User> userList(){
        return userRepository.findAll();
    }
}
