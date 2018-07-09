package com.springnews.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao extends JdbcDaoSupport {
    @Autowired
    UserRepository userRepository;

    public List<MyUser> userList(){
        return userRepository.findAll();
    }

    public List<MyUser> QueryAllUser() {

        String sql = "select uid,author,username from user";
        List<Map<String,Object>> list = getJdbcTemplate().queryForList(sql);
        List<MyUser> userList=new ArrayList<MyUser>();
        for(Map<String,Object> row:list)
        {
            MyUser user=new MyUser();
            user.setUid((Integer)row.get("uid"));
            user.setAuthor((String)row.get("author"));
            user.setUsername((String)row.get("username"));
            userList.add(user);
        }
        return userList;

    }


}
