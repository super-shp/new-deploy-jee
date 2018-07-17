package com.springnews.entity;

import java.util.List;

public class RegionList {
    private int total;
    private List<Region> columnList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Region> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Region> columnList) {
        this.columnList = columnList;
    }
}
