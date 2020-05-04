package com.next.newsweb.controller;

import com.next.newsweb.dto.ResultDTO;
import com.next.newsweb.model.User;
import com.next.newsweb.service.GbPwdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GbPwdController {

    @Autowired
    GbPwdService gbPwdService;

    @RequestMapping("/gbPwd")
    public String goGbPwd(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getPwd() == null) {
            return "redirect:https://github.com/settings/security";
        }
        return "gbPwd";
    }

    @ResponseBody
    @PostMapping("/modifyPwd")
    public ResultDTO modifyPwd(HttpServletRequest request,
                               @RequestParam String password) {
        User user = (User) request.getSession().getAttribute("user");//获取session里的user并且强转达到下条的user为空时跳转首页
        if (user == null) {
            return ResultDTO.info(303, "尚未登陆！");
        }
        if (StringUtils.isBlank(password)) {
            return ResultDTO.info(302, "密码不能为空");
        }
        return gbPwdService.modifyPwd(user.getAccountId(), password);
    }
}
