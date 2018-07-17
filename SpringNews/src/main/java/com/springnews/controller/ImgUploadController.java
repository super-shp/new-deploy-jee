package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springnews.entity.MyUser;
import com.springnews.enums.ResultEnum;
import com.springnews.qiniu.QiniuUpload;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class ImgUploadController {
    @PostMapping("/upload-img")
    public UnifyResponse<String> uploadImgQiniu(@RequestParam("uploadImg") MultipartFile multipartFile) throws IOException {
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        QiniuUpload qiniuUpload = new QiniuUpload();
        String path = qiniuUpload.fileUpLoad(inputStream, multipartFile.getOriginalFilename());
        return ResultUtil.successs(ResultEnum.SUCCESS, path);
    }
}
