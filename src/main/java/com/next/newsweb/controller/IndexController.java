package com.next.newsweb.controller;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsService newsService;

    @GetMapping("/")//访问首页时
    public String index(HttpServletRequest request, Model model) {//请求服务器返回浏览器设置cookie用response，请求cookie用request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {//循环所有cookie
                if (cookie.getName().equals("token")) {//getName获取cookie的键，如果name="token"，那么就跟数据库中token名字相同，他的值就是token的value
                    String token = cookie.getValue();//getValue获取对应的（键名为"token"的）值
                    User user = userMapper.findByToken(token);//拿叫"token"的cookie去数据库里查
                    if (user != null) {
                        request.getSession().setAttribute("user", user);//把user放进session，前端就能判断
                    }
                    break;
                }
            }
        }

        List<NewsDTO> newsList = newsService.list();
        model.addAttribute("newses", newsList);
        return "index";
    }
}
