CREATE TABLE `user`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `account_id`   varchar(100) DEFAULT NULL,
    `name`         varchar(50)  DEFAULT NULL,
    `avatar_url`   varchar(100) DEFAULT NULL,
    `token`        char(36)     DEFAULT NULL,
    `gmt_create`   bigint       DEFAULT NULL,
    `gmt_modified` bigint       DEFAULT NULL,
    `pwd`          varchar(30)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;