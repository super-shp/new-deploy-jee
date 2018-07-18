package com.springnews.entity;
import com.mongodb.MongoClient;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class NewsEntity implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private int pid;
    private DBObject content;

    public int getPid() {
        return pid;
    }

    public DBObject getContent() {
        return content;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setContent(DBObject content) {
        this.content = content;
    }
}
