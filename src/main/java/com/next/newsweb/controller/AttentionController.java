package com.next.newsweb.controller;

import com.next.newsweb.cache.HotTagCache;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AttentionController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/attention")//访问首页时
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "creator", required = false) Long creator,
                        @RequestParam(name = "sort", required = false) String sort) {//请求服务器返回浏览器设置cookie用response，请求cookie用request
        PaginationDTO pagination = newsService.list(search, tag, creator, sort, page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tags);
        model.addAttribute("creator", creator);
        model.addAttribute("sort", sort);
        return "attention";
    }
}
