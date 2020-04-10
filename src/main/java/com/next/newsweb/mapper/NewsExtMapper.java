package com.next.newsweb.mapper;

import com.next.newsweb.model.News;

import java.util.List;

public interface NewsExtMapper {
    int incView(News record);

    int incCommentCount(News record);//路由到resource/mapper

    List<News> selectRelated(News news);
}