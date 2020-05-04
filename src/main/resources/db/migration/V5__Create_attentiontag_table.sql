CREATE TABLE `attentiontag`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `userid`       bigint                                                        DEFAULT NULL,
    `attentiontag` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;