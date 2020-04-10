CREATE TABLE `notification`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `notifier`      bigint NOT NULL COMMENT '通知的人',
    `notifier_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通知的人的名字',
    `receiver`      bigint NOT NULL COMMENT '接收消息的人  ',
    `outerId`       bigint NOT NULL COMMENT '新闻/评论的ID',
    `outer_title`   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '新闻/评论的标题',
    `type`          int    NOT NULL COMMENT '新闻/评论类型',
    `gmt_create`    bigint NOT NULL,
    `status`        int    NOT NULL                                               DEFAULT '0' COMMENT '0未读1已读',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;