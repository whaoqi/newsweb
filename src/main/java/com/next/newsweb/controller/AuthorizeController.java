package com.next.newsweb.controller;

import com.next.newsweb.dto.AccessTokenDTO;
import com.next.newsweb.dto.GithubUser;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired//将容器里写好的实例化实例记载到当前使用的上下文
    private GithubProvider githubProvider;
    //调用githubprovider方法

    @Value("${github_client_id}")
    public String clientId;
    @Value("${github_client_secret}")
    private String clientSecret;
    @Value("${github_redirect_uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;//https://www.cnblogs.com/Howinfun/p/11731826.html

    @GetMapping("/callback")//调用callback时拿到code，state调用client code 传回去调用accessToken拿到string
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);//access_token携带code
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);//github登陆成功后
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();//生成token
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAvatar_url(githubUser.getAvatar_url());
            user.setAccountId(String.valueOf(githubUser.getId()));//ID为long类型强转为string
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);//存储到数据库中
            response.addCookie(new Cookie("token", token));//通过response写入cookie，把token放到cookie里面
            return "redirect:/";//重定向请求地址
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}
