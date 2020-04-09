CREATE TABLE `comment`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `parent_id`     bigint NOT NULL COMMENT '父类ID',
    `type`          int    NOT NULL COMMENT '父类类型',
    `commentator`   bigint NOT NULL COMMENT '评论人ID',
    `content`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `gmt_create`    bigint NOT NULL,
    `gmt_modified`  bigint NOT NULL,
    `like_count`    bigint                                                         DEFAULT '0',
    `comment_count` int                                                            DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;