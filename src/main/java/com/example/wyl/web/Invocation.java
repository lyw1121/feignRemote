package com.example.wyl.web;

import com.example.wyl.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wyl-spring-cloud-consumer")
public interface Invocation {
    //添加用户
    @RequestMapping("insertUser")
    String insertUser( User user);
    //删除用户
    @RequestMapping("deleteUser")
    void deleteUser(@RequestParam("id") String id);
    //修改用户
    @RequestMapping("updateUser")
    String updateUser(@RequestParam("id") String id,@RequestParam("uname") String uname,@RequestParam("password") String password);
    //查询用户
    @RequestMapping("listUser")
    String listUser();
}