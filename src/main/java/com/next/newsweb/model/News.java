package com.next.newsweb.model;

import lombok.Data;

@Data
public class News {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
