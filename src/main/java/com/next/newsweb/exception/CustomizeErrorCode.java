package com.next.newsweb.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    NEWS_NOT_FOUND(2001, "你找到新聞不存在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何新聞或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "这是读别人的信息？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "是不是走错房间了？"),

    EMAIL_IS_EXIST(201, "邮箱已经注册过了！"),
    EMAIL_ILLEGAL(202, "邮箱不合法！"),
    EMAIL_OR_PWD_BLANK(203, "邮箱和密码都不能为空！"),
    INVALID_ADDRESSES(204, "邮箱不正确，无效的地址！"),
    REGISTER_FAIL(207, "注册失败"),
    EMAIL_OR_PWD_ERROR(208, "邮箱号或密码错误，登录失败"),
    EMAIL_IS_BLANK(209, "邮箱不能为空");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
