package com.next.newsweb.dto;

import lombok.Data;

@Data
public class NewsQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
