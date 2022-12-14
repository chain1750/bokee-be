CREATE TABLE `article`
(
    `id`               BIGINT UNSIGNED NOT NULL,
    `create_time`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `title`            VARCHAR(100)    NOT NULL COMMENT '文章标题',
    `article_abstract` VARCHAR(100)    NOT NULL COMMENT '文章摘要',
    `cover`            VARCHAR(500)    NOT NULL COMMENT '封面',
    `category_name`    VARCHAR(30)     NOT NULL COMMENT '类别名称',
    `tags`             VARCHAR(500)    NOT NULL COMMENT '标签',

    PRIMARY KEY (`id`)
) COMMENT = '文章';

CREATE TABLE `article_content`
(
    `id`      BIGINT UNSIGNED NOT NULL,
    `content` TEXT            NOT NULL COMMENT '文章内容',

    PRIMARY KEY (`id`)
) COMMENT = '文章内容';