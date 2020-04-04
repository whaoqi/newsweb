package com.next.newsweb.controller;

import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            return "redirect:/";
        }

        if ("newses".equals(action)) {
            model.addAttribute("section", "newses");
            model.addAttribute("sectionName", "我的发布");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        PaginationDTO paginationDTO = newsService.list(user.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}