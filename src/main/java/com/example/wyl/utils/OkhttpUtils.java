package com.example.wyl.utils;

import com.example.wyl.model.User;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OkhttpUtils {

    //Map<Object, Object> objectMap = new HashMap<>();

    public String okhttpTest(String url, Map<String, String> map) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
                   builder .add(entry.getKey(), entry.getValue())
                    .build();
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();







//        RequestBody requestBody = new FormBody.Builder()
//
//                .add("id","id")
//                .add("name","name")
//                .add("dept","dept")
//                .add("phone","phone")
//                .add("uname","uname")
//                .add("password","password")
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        //创建request对象
//        Response response = client.newCall(request).execute();
//        return response.body().string();
    }
}
