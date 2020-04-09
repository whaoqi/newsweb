package com.next.newsweb.mapper;

import com.next.newsweb.model.News;

public interface NewsExtMapper {
    int incView(News record);
    int incCommentCount(News record);//路由到resource/mapper
}