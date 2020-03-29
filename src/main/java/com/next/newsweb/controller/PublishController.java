package com.next.newsweb.controller;

import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")//非前后端分离
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request, Model model) {
        model.addAttribute("title", title);//接收后放在model里为了验证是否为空、回显
        model.addAttribute("content", content);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "新闻标题不能为空");
            return "publish";
        }
        if (content == null || content == "") {
            model.addAttribute("error", "新闻内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        User user = null;//是否登陆
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {//循环所有cookie
                if (cookie.getName().equals("token")) {//getName获取cookie的键，如果name="token"，那么就跟数据库中token名字相同，他的值就是token的value
                    String token = cookie.getValue();//getValue获取对应的（键名为"token"的）值
                    user = userMapper.findByToken(token);//拿叫"token"的cookie去数据库里查
                    if (user != null) {
                        request.getSession().setAttribute("user", user);//把user放进session，前端就能判断
                    }
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setTag(tag);
        news.setCreator(user.getId());
        news.setGmtCreate(System.currentTimeMillis());
        news.setGmtModified(news.getGmtCreate());
        newsMapper.create(news);//存入数据库
        return "redirect:/";
    }
}