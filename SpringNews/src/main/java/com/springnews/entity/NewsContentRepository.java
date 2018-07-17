package com.springnews.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsContentRepository extends MongoRepository<NewsContent, String> {
    public NewsContent findByPid(int pid);
}
