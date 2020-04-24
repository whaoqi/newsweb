package com.next.newsweb.controller;

import com.next.newsweb.cache.HotTagCache;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.model.Attentiontag;
import com.next.newsweb.model.User;
import com.next.newsweb.service.NewsService;
import com.next.newsweb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")//访问首页时
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
        return "index";
    }

    @PostMapping("/addtag/{tag}")
    public String addTag(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "tag", required = false) String tag,
            HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            System.out.println("fail");
            return "index";
        }

        Attentiontag attentiontag = new Attentiontag();
        attentiontag.setUserid(user.getId());
        attentiontag.setAttentiontag(tag);
        tagService.addtag(attentiontag);
        return "redirect:/";
    }

    @PostMapping("/deltag/{tag}")
    public String deleteTag(
            @PathVariable(name = "tag") String tag,
            HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "index";
        }

        Attentiontag attentiontag = new Attentiontag();
        attentiontag.setUserid(user.getId());
        attentiontag.setAttentiontag(tag);
        tagService.deletetag(attentiontag);
        return "redirect:/";
    }
}
