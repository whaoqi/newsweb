package com.next.newsweb.dto;

import com.next.newsweb.model.User;
import lombok.Data;

@Data
public class AttentiontagDTO {
    private Long id;
    private Long userid;
    private String attentiontag;

    private User user;
}
