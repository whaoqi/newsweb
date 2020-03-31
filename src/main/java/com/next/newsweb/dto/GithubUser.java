package com.next.newsweb.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;//fastjson可把驼峰映射为下划线
    //GithubUser是GithubProvider的返回值
}
