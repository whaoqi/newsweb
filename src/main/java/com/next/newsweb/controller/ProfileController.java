package com.next.newsweb.controller;

import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NewsService;
import com.next.newsweb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "15") Integer size) {
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            return "redirect:/";
        }

        if ("newses".equals(action)) {
            PaginationDTO paginationDTO = newsService.list(user.getId(), page, size);
            model.addAttribute("section", "newses");
            model.addAttribute("sectionName", "我的发布");
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }

    @PostMapping("/delnewsp/{id}")
    public String deleteNews(
            @PathVariable("id") Long id) {
        newsService.delete(id);
        return "redirect:/profile/newses/";
    }
}