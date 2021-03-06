package com.next.newsweb.controller;

import com.next.newsweb.dto.CommentDTO;
import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.enums.CommentTypeEnum;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.exception.CustomizeException;
import com.next.newsweb.service.CommentService;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/news/{id}")
    public String news(@PathVariable(name = "id") String id, Model model) {
        Long newsId = null;
        try {
            newsId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        NewsDTO newsDTO = newsService.getById(newsId);
        List<NewsDTO> relatedNewses = newsService.selectRelated(newsDTO);
        List<CommentDTO> comments = commentService.listByTargetId(newsId, CommentTypeEnum.NEWS);
        //累加阅读数
        newsService.incView(newsId);
        model.addAttribute("news", newsDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedNewses", relatedNewses);
        return "news";
    }

/*    @PostMapping("/like/{id}")
    public String likeNews(
            @PathVariable("id") Long id) {
        newsService.like(id);
        return "redirect:/";
    }*/

    @PostMapping("/delnews/{id}")
    public String deleteNews(
            @PathVariable("id") Long id) {
        newsService.delete(id);
        return "redirect:/";
    }
}
