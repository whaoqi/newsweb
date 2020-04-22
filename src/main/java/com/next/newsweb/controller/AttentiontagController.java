package com.next.newsweb.controller;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.model.User;
import com.next.newsweb.service.AttentiontagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AttentiontagController {

    @Autowired
    private AttentiontagService attentiontagService;

    @GetMapping("/attention")
    public String attentiontag(HttpServletRequest request, Model model,
                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                            @RequestParam(name = "tag", required = false) String tag) {//请求服务器返回浏览器设置cookie用response，请求cookie用request
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            return "redirect:/";
        }

        PaginationDTO pagination = attentiontagService.list(user.getId(), tag, page, size);

        model.addAttribute("pagination", pagination);
        model.addAttribute("tag", tag);
        return "attention";
    }
}
