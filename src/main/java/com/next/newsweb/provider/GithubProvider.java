package com.next.newsweb.provider;

import com.alibaba.fastjson.JSON;
import com.next.newsweb.dto.AccessTokenDTO;
import com.next.newsweb.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//使其放在spring容器里
public class GithubProvider {//返回access_token

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //POST
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));//accessTokenDTO转为json
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=" + accessTokenDTO.getClient_id() + "&client_secret=" + accessTokenDTO.getClient_secret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirect_uri() + "&state=" + accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();//得到string对象
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];//示例：access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //通过access_token获取用户信息
    public GithubUser getUser(String accessToken) {
        //GET
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();//得到response也即JSON格式
            String string = response.body().string();//得到string对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//将string JSON对象转换为JAVA类对象
            return githubUser;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
