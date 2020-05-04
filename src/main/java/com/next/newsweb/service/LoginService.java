package com.next.newsweb.service;

import com.next.newsweb.dto.ResultDTO;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    public Object login(User user, HttpServletResponse response) {
        String email = user.getAccountId();
        String password = user.getPwd();
        if (email == null || password == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_OR_PWD_BLANK);
        }
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(email)
                .andPwdEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 1) {
            response.addCookie(new Cookie("token", users.get(0).getToken()));
            return ResultDTO.info(200, "登陆成功");
        }
        return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_OR_PWD_ERROR);
    }
}
