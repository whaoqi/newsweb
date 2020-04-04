package com.next.newsweb.controller;

import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private NewsMapper newsMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")//非前后端分离
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
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
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
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