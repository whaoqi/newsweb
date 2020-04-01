package com.next.newsweb.mapper;

import com.next.newsweb.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Insert("insert into news(title,content,gmt_create,gmt_modified,creator,tag) values (#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(News news);

    @Select("select * from news limit #{offset}, #{size}")
    List<News> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(*) from news")
    Integer count();
}
