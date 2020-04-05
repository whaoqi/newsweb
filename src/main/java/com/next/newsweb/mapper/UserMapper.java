package com.next.newsweb.mapper;

import com.next.newsweb.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,avatar_url,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{avatarUrl},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);//形参为类的时候会自动将类中属性带入到#{}里的值

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//形参不为类时需要注解@Param

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}
