package com.example.wyl.dao;

import com.example.wyl.model.Log;
import org.springframework.stereotype.Repository;

@Repository
public interface logMapper {
    void insertLog(Log log);
}
