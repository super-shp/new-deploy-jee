package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springnews.entity.MyUser;
import com.springnews.qiniu.QiniuUpload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class ImgUploadController {
    @PostMapping("/upload-img")
    public String uploadImgQiniu(@RequestParam("uploadImg") MultipartFile multipartFile) throws IOException {
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        QiniuUpload qiniuUpload = new QiniuUpload();
        String path = qiniuUpload.fileUpLoad(inputStream, multipartFile.getOriginalFilename());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("errorCode", 200);
        jsonObject.addProperty("msg", "OK");
        jsonObject.addProperty("data", path);
        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }
}
