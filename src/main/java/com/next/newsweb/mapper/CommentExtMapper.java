package com.next.newsweb.mapper;

import com.next.newsweb.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
    int incCommentCountZ(Comment comment);
}