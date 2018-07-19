package com.springnews.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    public Region findByRegionNameLike(String filter);
    public Region findByRegionName(String filter);
    public Region findByCid(int cid);
}
