package com.next.newsweb.controller;

import com.next.newsweb.dto.MyUserDTO;
import com.next.newsweb.dto.ResultDTO;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @ResponseBody
    @PostMapping("/register")
    public Object register(MyUserDTO userDTO) {
        if (StringUtils.isBlank(userDTO.getAccountId()) && StringUtils.isBlank(userDTO.getPassword())) {
            return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_OR_PWD_BLANK);
        }
        boolean flag = registerService.checkRegister(userDTO.getAccountId());
        if (!flag) {
            return ResultDTO.info(303, "邮箱用户存在");
        }
        //表示不能注册 邮箱名重复
        return registerService.register(userDTO);
    }
}
