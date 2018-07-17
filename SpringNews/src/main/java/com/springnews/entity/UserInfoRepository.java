package com.springnews.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    public UserInfo findByUid(String uid);
}
