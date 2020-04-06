package com.next.newsweb.service;

import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.User;
import com.next.newsweb.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        //User dbUser = userMapper.findByAccountId(user.getAccountId());//AccountId唯一
/*      @Select("select * from user where account_id = #{accountId}")
        User findByAccountId(@Param("accountId") String accountId);*/
        if (users.size() == 0) {
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);//updateByExampleSelective:不为空时再传.example更新为updateUser
            //userMapper.update(dbUser);
/*          @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
            void update(User dbUser);*/
        }
    }
}
