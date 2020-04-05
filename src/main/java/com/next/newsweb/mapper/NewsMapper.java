package com.next.newsweb.mapper;

import com.next.newsweb.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    @Insert("insert into news(title,content,gmt_create,gmt_modified,creator,tag) values (#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(News news);

    @Select("select * from news limit #{offset}, #{size}")
    List<News> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(*) from news")
    Integer count();

    @Select("select * from news where creator = #{userId} limit #{offset}, #{size}")
    List<News> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from news where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from news where id = #{id}")
    News getById(@Param("id") Integer id);

    @Update("update news set title = #{title}, content = #{content}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(News news);
}
