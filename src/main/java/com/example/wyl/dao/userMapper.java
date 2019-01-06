package com.example.wyl.dao;

import com.example.wyl.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface userMapper {
    //登入
    User confirm(User user);
}
