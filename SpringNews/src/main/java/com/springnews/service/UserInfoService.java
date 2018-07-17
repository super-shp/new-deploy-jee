package com.springnews.service;

import com.springnews.entity.UserInfo;
import com.springnews.entity.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findByUid(String uid){
        return userInfoRepository.findByUid(uid);
    }
}
