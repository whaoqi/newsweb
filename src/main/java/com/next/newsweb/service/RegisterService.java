package com.next.newsweb.service;

import com.next.newsweb.dto.MyUserDTO;
import com.next.newsweb.dto.ResultDTO;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserMapper userMapper;

    //默认头像
    @Value("${customize.defaultAvatar}")
    String defaultAvatarUrl;

    //检查邮箱是否注册
    public boolean checkRegister(String accountId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            return true;
        }
        return false;
    }

    public Object register(MyUserDTO userDTO) {
        User user = new User();
        user.setName("邮箱用户_" + userDTO.getAccountId());
        user.setPwd(userDTO.getPassword());
        user.setAccountId(userDTO.getAccountId());
        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        //设置默认头像
        user.setAvatarUrl(defaultAvatarUrl);
        int flag = userMapper.insert(user);
        //注册成功
        if (flag == 1) {
            return ResultDTO.info(200, "注册成功");
        }
        //注册失败
        return ResultDTO.errorOf(CustomizeErrorCode.REGISTER_FAIL);
    }

}
