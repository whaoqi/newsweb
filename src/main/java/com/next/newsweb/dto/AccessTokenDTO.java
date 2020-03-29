package com.next.newsweb.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    //>=两个参数建议封装成类
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
