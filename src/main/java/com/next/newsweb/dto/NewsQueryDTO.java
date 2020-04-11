package com.next.newsweb.dto;

import lombok.Data;

@Data
public class NewsQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
