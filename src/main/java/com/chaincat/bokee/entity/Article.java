package com.chaincat.bokee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;

    protected LocalDateTime createTime;

    protected LocalDateTime updateTime;

    protected String title;

    protected String articleAbstract;

    protected String cover;

    protected String categoryName;

    protected String tags;
}
