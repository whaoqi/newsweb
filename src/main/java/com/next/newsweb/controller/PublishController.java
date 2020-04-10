package com.next.newsweb.controller;

import com.next.newsweb.cache.TagCache;
import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.model.News;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        NewsDTO news = newsService.getById(id);
        model.addAttribute("title", news.getTitle());
        model.addAttribute("content", news.getContent());
        model.addAttribute("tag", news.getTag());
        model.addAttribute("id", news.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")//非前后端分离
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request, Model model) {
        model.addAttribute("title", title);//接收后放在model里为了验证是否为空、回显
        model.addAttribute("content", content);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

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

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
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
        news.setId(id);//可能空
        newsService.createOrUpdate(news);
        return "redirect:/";
    }
}