package com.springnews.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    public News findByPid(int pid);
    public void deleteByPid(int pid);
    public Page<News> findByStatus(int status, Pageable pageable);
    public List<News> findByStatus(int status);
}
