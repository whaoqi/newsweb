package com.next.newsweb.dto;

import lombok.Data;

@Data
public class NewsQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private Long creator;
    private String tag;
    private Integer page;
    private Integer size;
    private Long reader;
}
