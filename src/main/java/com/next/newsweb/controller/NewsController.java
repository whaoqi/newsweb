package com.next.newsweb.controller;

import com.next.newsweb.dto.CommentDTO;
import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.enums.CommentTypeEnum;
import com.next.newsweb.service.CommentService;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/news/{id}")
    public String news(@PathVariable(name = "id") Long id, Model model) {
        NewsDTO newsDTO = newsService.getById(id);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.NEWS);
        //累加阅读数
        newsService.incView(id);
        model.addAttribute("news", newsDTO);
        model.addAttribute("comments", comments);
        return "news";
    }
}
