package com.springnews.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    public News findByPid(int pid);
    public void deleteByPid(int pid);
}
