CREATE TABLE `news`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `title`         varchar(50)  DEFAULT NULL,
    `content`       text,
    `gmt_create`    bigint       DEFAULT NULL,
    `gmt_modified`  bigint       DEFAULT NULL,
    `creator`       bigint       DEFAULT NULL,
    `view_count`    int          DEFAULT '0',
    `comment_count` int          DEFAULT '0',
    `like_count`    int          DEFAULT '0',
    `tag`           varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;