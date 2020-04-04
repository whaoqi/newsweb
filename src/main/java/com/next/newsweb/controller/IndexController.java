package com.next.newsweb.controller;

import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/")//访问首页时
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {//请求服务器返回浏览器设置cookie用response，请求cookie用request
        PaginationDTO pagination = newsService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
