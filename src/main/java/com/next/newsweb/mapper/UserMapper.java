package com.next.newsweb.mapper;

import com.next.newsweb.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,avatar_url,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{avatarUrl},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);//形参为类的时候会自动将类中属性带入到#{}里的值

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//形参不为类时需要注解@Param

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

}
