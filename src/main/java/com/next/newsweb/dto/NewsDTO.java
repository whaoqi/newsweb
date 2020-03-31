package com.next.newsweb.dto;

import com.next.newsweb.model.User;
import lombok.Data;

@Data
public class NewsDTO {
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
    private User user;
}
