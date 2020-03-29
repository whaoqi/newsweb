package com.next.newsweb.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
    //GithubUser是GithubProvider的返回值
}
