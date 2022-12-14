package com.chaincat.bokee.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类VO
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    private String categoryName;

    private Long count;
}
