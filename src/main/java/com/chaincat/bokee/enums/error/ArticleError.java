package com.chaincat.bokee.enums.error;

import com.chaincat.bokee.common.exception.IError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章业务异常
 * @author Chain
 */
@Getter
@AllArgsConstructor
public enum ArticleError implements IError {

    /**
     * 文章业务异常
     */
    ARTICLE_NOT_FOUND(300000, "文章不存在"),
    ;

    private final Integer code;

    private final String msg;
}
