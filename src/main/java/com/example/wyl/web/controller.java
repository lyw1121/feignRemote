package com.example.wyl.web;

import com.alibaba.fastjson.JSON;
import com.djcps.redis.client.RedisClient;
import com.example.wyl.dao.userMapper;
import com.example.wyl.model.User;
import com.example.wyl.utils.OkhttpUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class controller {
    @Autowired
    Invocation invocation;
    @Autowired
    userMapper userMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    AmqpTemplate rabbitTemplate;
    @Autowired
    OkhttpUtils okhttpUtils;

    public controller() throws IOException {
    }

    @RequestMapping("login")
    public String hello(@RequestParam("uname") String uname, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        User user = new User(uname, password);
        User confirm = userMapper.confirm(user);
        String cookieId = UUID.randomUUID().toString();

        if (confirm != null) {
            Cookie cookie = new Cookie("token",cookieId);
            response.addCookie(cookie);
            String redval = JSON.toJSONString(confirm);
            redisClient.set(cookieId,redval);
            redisClient.expire(cookieId,5000);
            return "登入成功!";
        } else {
            System.out.println("查找不到用户！");
            return "查找不到用户!";
        }
    }
    //调用添加
    @RequestMapping("insertUser")
    public String insert(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("dept") String dept,
                             @RequestParam("phone") String phone,
                             @RequestParam("uname") String uname,
                             @RequestParam("password") String password,
                         @CookieValue("token") String token){

        User user = new User();
        user.setPassword(password);
        user.setName(name);
        user.setUname(uname);
        user.setId(id);
        user.setDept(dept);
        user.setPhone(phone);
        String ins = redisClient.get(token);
        String inss = "添加用户"+ins;
        send(inss);
        return invocation.insertUser(user);
    }
    //调用删除
    @RequestMapping("deleteUser")
    public void delete(@RequestParam("id") String id,@CookieValue("token") String token){
        invocation.deleteUser(id);
        String del = redisClient.get(token);
        String dels = "删除用户"+del;
        send(dels);
    }
    //调用修改
    @RequestMapping("updateUser")
    public void update(@RequestParam("id") String id,
                       @RequestParam("uname") String uname,
                       @RequestParam("password") String password,
                       @CookieValue("token") String token){
        invocation.updateUser(id,uname,password);
        String up = redisClient.get(token);
        String ups = "修改指定用户"+up;
        send(ups);
    }
    //调用查询用户
    @RequestMapping("listUser")
    public String listUser(@CookieValue("token") String token){
        System.out.println("打印出来");
        String s = invocation.listUser();
        String s1= redisClient.get(token);
        String s2 = "查询所有用户11111;" + s1;
        send(s2);
        return s;
    }
    //发送数据到MQ
    public void send( String Msg){
        this.rabbitTemplate.convertAndSend("directExchange","testHello",Msg);
    }

   /**
    *
    *
    * okhttp调用
    *
    * **/
   //查询所有
    @RequestMapping("okhttplist")
    public String okhttpTest() throws IOException {
        String url="http://localhost:8080/listUser";
        Map<String,String> objectMap = new HashMap<>();
        return okhttpUtils.okhttpTest(url,objectMap);
    }
    //删除用户
    @RequestMapping("okhttpDel")
    public String okhttpDel(@RequestParam("id") String id) throws IOException {
        Map<String,String> objectMap = new HashMap<>();
        objectMap.put("id",id);
        String url="http://localhost:8080/deleteUser";
        return okhttpUtils.okhttpTest(url,objectMap);
    }
    //插入用户
    @RequestMapping(value = "okhttpIns")
    public String okhttpIns(@RequestParam("id") String id,
                            @RequestParam("name") String name,
                            @RequestParam("dept") String dept,
                            @RequestParam("phone") String phone,
                            @RequestParam("uname") String uname,
                            @RequestParam("password") String password) throws IOException {
        Map<String,String> objectMap = new HashMap<>();
        objectMap.put("id",id);
        objectMap.put("name",name);
        objectMap.put("dept",dept);
        objectMap.put("phone",phone);
        objectMap.put("uname",uname);
        objectMap.put("password",password);
        String url="http://localhost:8080/insertUser";
        return okhttpUtils.okhttpTest(url,objectMap);
    }
    //修改用户
    @RequestMapping("okhttpUpd")
    public String okhttpUpd(@RequestParam("id") String id,
                            @RequestParam("uname") String uname,
                            @RequestParam("password") String password) throws IOException {
        Map<String,String> objectMap = new HashMap<>();
        objectMap.put("id",id);
        objectMap.put("uname",uname);
        objectMap.put("password",password);
        String url = "http://localhost:8080/updateUser";
        return okhttpUtils.okhttpTest(url,objectMap);
    }

}
