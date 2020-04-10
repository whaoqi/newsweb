package com.next.newsweb.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;//通知展示的新闻标题
    private Long outerid;
    private String typeName;
    private Integer type;
}
