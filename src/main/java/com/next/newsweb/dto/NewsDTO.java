package com.next.newsweb.dto;

import com.next.newsweb.model.User;
import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
