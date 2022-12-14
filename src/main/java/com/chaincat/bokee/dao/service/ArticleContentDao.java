package com.chaincat.bokee.dao.service;

import com.chaincat.bokee.dao.mapper.ArticleContentMapper;
import com.chaincat.bokee.entity.ArticleContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Chain
 */
@Service
@RequiredArgsConstructor
public class ArticleContentDao{

    private final ArticleContentMapper articleContentMapper;

    public int insert(Long id, String content) {
        return articleContentMapper.insert(new ArticleContent(id, content));
    }

    public int update(Long id, String content) {
        return articleContentMapper.updateById(new ArticleContent(id, content));
    }
}
