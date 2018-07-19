package com.springnews.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    public News findByPid(int pid);

    @Transactional
    public void deleteByPid(int pid);

    public Page<News> findByStatus(int status, Pageable pageable);
    public List<News> findByStatus(int status);

    public Page<News> findAllByCid(int cid, Pageable pageable);
    public Page<News> findAllByCidAndStatus(int cid, int status, Pageable pageable);

    public Page<News> findAllByTitleLike(String filter, Pageable pageable);
    public Page<News> findAllByTitleLikeAndStatus(String filter, int status, Pageable pageable);
}
