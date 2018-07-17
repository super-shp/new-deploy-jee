package com.springnews.entity;

import java.util.ArrayList;
import java.util.List;

public class NewsList {
    private int total;
    private int offset;
    private List<News> articleList;

    public NewsList(){
        articleList = new ArrayList<News>();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<News> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<News> articleList) {
        this.articleList = articleList;
    }
}
