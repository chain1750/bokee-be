package com.chaincat.bokee.entity.extend;

import com.chaincat.bokee.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文章JOIN
 * @author Chain
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleJoin extends Article {

    private String content;
}
