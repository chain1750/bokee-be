package com.chaincat.bokee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章内容
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleContent {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String content;
}
