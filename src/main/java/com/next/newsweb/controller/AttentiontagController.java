package com.next.newsweb.controller;

import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.model.Attentiontag;
import com.next.newsweb.model.User;
import com.next.newsweb.service.AttentiontagService;
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
public class AttentiontagController {

    @Autowired
    private AttentiontagService attentiontagService;

    @Autowired
    private TagService tagService;

    @GetMapping("/attention")
    public String attentiontag(HttpServletRequest request, Model model,
                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "size", defaultValue = "10") Integer size,
                               @RequestParam(name = "tag", required = false) String tag) {//请求服务器返回浏览器设置cookie用response，请求cookie用request
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            return "redirect:/";
        }

        List<Attentiontag> attentiontags = attentiontagService.taglist(user.getId());
        PaginationDTO pagination = attentiontagService.list(tag, page, size);

        model.addAttribute("pagination", pagination);
        model.addAttribute("attentiontags", attentiontags);
        model.addAttribute("tag", tag);
        return "attention";
    }

    @PostMapping("/deltagt/{tag}")
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
        return "redirect:/attention";
    }
}
