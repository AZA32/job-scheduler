CREATE database job;

CREATE TABLE `sys_job`
(
    `id`         int                                                      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '任务名称',
    `cron`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'cron表达式',
    `class_path` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类全路径',
    `data_map`   varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '传递参数',
    `status`     int                                                      NOT NULL COMMENT '状态:1启用 0停用',
    `describe`   varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务功能描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb3;