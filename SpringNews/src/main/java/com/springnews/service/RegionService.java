package com.springnews.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.springnews.entity.Region;
import com.springnews.entity.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public JsonObject getRegionList(){
        List<Region> regionList = regionRepository.findAll();
        if(regionList.size() == 0){
            return null;
        }
        JsonObject regionJson = new JsonObject();
        regionJson.addProperty("total", regionList.size());
        JsonArray regionJsonArray = new JsonArray();

        for(int i = 0; i < regionList.size(); i++){
            JsonObject region = new JsonObject();
            region.addProperty("cid", regionList.get(i).getCid());
            region.addProperty("column_name", regionList.get(i).getRegion_name());
            regionJsonArray.add(region);
        }

        regionJson.add("columnList", regionJsonArray);
        return regionJson;
    }
}
