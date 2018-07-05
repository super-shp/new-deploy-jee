package com.springnews.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatTransform {
    public Date timeStampToDate(String str){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            d.setTime(sdf.parse(str).getTime());
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String dateToTimeStamp(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
