package com.springnews.entity;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Document
public class NewsContent implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;

    private int pid;
    private int words;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }
}
