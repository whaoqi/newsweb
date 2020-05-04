package com.next.newsweb.controller;

import com.next.newsweb.model.User;
import com.next.newsweb.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public String goLogin() {
        return "loginRegisterForm";
    }

    @ResponseBody
    @PostMapping("/login")
    public Object login(User user, HttpServletResponse response) {
        return loginService.login(user, response);
    }


}

