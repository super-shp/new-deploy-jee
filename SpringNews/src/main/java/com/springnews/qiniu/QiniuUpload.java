package com.springnews.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;

import javax.security.auth.login.AppConfigurationEntry;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.FileInputStream;

public class QiniuUpload {
    private QiniuAuth qiniuAuth;
    private Configuration cfg;
    private UploadManager uploadManager;

    public QiniuUpload(){
        qiniuAuth = new QiniuAuth();
        cfg = new Configuration(Zone.zone1());
        uploadManager = new UploadManager(cfg);
    }

    //参数为文件路径以及文件名,文件名要求带后缀比如.jpg
    public String fileUpLoad(FileInputStream file, String key){
        String upToken = qiniuAuth.getAuth().uploadToken(qiniuAuth.getBucket());
        try {
            Response response = uploadManager.put(file, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return qiniuAuth.getPath() + "/" + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }
    }
}
