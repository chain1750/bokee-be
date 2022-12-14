package com.chaincat.bokee.vo;

import com.chaincat.bokee.common.constant.TimeConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章VO
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonFormat(pattern = TimeConstant.DATE_TIME1)
    private LocalDateTime createTime;

    private String title;

    private String articleAbstract;

    private String content;

    private String cover;

    private String categoryName;

    private List<String> tagList;

    private Integer year;

    private Integer month;
}
