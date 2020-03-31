package com.next.newsweb.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String avatarUrl;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
