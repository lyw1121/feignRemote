package com.example.wyl.model;

import javax.xml.crypto.Data;
import java.util.Date;

//存入日志信息
public class Log {
    public String id;
    public String msg;
    public String action;
    public Date ftime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getFtime() {
        return ftime;
    }

    public void setFtime(Date ftime) {
        this.ftime = ftime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", action='" + action + '\'' +
                ", ftime=" + ftime +
                '}';
    }
}
