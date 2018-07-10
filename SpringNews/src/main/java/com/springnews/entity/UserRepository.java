package com.springnews.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Integer>{

    public MyUser findByUsername(String username);

    public MyUser findByUid(String uid);

}
