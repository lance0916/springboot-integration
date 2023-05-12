CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`      INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`    VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '姓名',
    `age`     INT           NOT NULL DEFAULT 0 COMMENT '年龄',
    `birth`   TIMESTAMP     NULL COMMENT '生日',
    `balance` DECIMAL(8, 2) NOT NULL DEFAULT 0 COMMENT '余额'
) ENGINE=InnoDb CHARSET=Utf8mb4;