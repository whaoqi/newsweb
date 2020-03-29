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
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag, HttpServletRequest request, Model model) {

        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
        for (Cookie cookie : cookies) {//循环所有cookie
            if (cookie.getName().equals("token")) {//getName获取cookie的键，如果name="token"，那么就跟数据库中token名字相同，他的值就是token的value
                String token = cookie.getValue();//getValue获取对应的（键名为"token"的）值
                user = userMapper.findByToken(token);//拿叫"token"的cookie去数据库里查
                if (user != null) {
                    request.getSession().setAttribute("user", user);//把user放进session，前端就能判断
                }
                break;
            }
        }}
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
        newsMapper.create(news);
        return "redirect:/";
    }
}
   /* @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }



            Model model
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");


        */
