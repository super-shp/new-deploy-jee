package com.springnews.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.springnews.entity.Region;
import com.springnews.entity.RegionList;
import com.springnews.entity.RegionRepository;
import com.springnews.enums.ResultEnum;
import com.springnews.exception.NewsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionList getRegionList() throws Exception{
        List<Region> allRegion = regionRepository.findAll();
        if(allRegion.size() <= 0){
            throw new NewsException(ResultEnum.QUERY_ERROR);
        }
        RegionList regionList = new RegionList();
        regionList.setTotal(allRegion.size());
        regionList.setColumnList(allRegion);
        return regionList;
    }

    public Region findByRegionNameLike(String filter){
        return regionRepository.findByRegionNameLike(filter);
    }

    public Region findByRegionName(String filter){
        return regionRepository.findByRegionName(filter);
    }

    public Region findByCid(int cid){
        return regionRepository.findByCid(cid);
    }
}
