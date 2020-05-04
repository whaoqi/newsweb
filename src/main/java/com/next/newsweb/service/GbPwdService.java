package com.next.newsweb.service;

import com.next.newsweb.dto.ResultDTO;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class GbPwdService {

    @Autowired
    UserMapper userMapper;

    public ResultDTO modifyPwd(String email, String password) {
        User user = new User();
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(email);
        user.setPwd(password);
        int update = userMapper.updateByExampleSelective(user, userExample);
        if (update == 1) {
            return ResultDTO.info(200, "密码修改成功");
        }
        return ResultDTO.info(302, "密码修改失败");
    }
}
