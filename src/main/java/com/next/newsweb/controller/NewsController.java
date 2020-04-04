package com.next.newsweb.controller;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news/{id}")
    public String news(@PathVariable(name = "id") Integer id,
                           Model model) {
        NewsDTO newsDTO = newsService.getById(id);
        model.addAttribute("news", newsDTO);
        return "news";
    }
}
