package com.springnews.qiniu;

import com.qiniu.util.Auth;

public class QiniuAuth {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private Auth auth;
    private String path;

    public QiniuAuth(){
        accessKey = "RndofQdmMjHPa-1PbGQhMhYIX50U2OvE24rN_3Iv";
        secretKey = "XlpkPXtk90wDD_CuTqL4wk3rkez7KC7zjFN39tsO";
        bucket = "buzhidao";
//        accessKey = "此处输入七牛云的访问密钥";
//        secretKey = "此处输入七牛云的访问密钥";
//        bucket = "此处输入你的bucket的名字";
        auth = Auth.create(accessKey, secretKey);
        path = "pboyzskrt.bkt.clouddn.com";
    }

    public Auth getAuth() {
        return auth;
    }

    public String getBucket() {
        return bucket;
    }

    public String getPath() {
        return path;
    }
}
