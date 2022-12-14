package com.chaincat.bokee.common.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chaincat.bokee.common.utils.BeanCopyUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 分页结果
 *
 * @author Chain
 */
@Getter
@RequiredArgsConstructor
public class PageResult<T> {

    /**
     * 当前页索引
     */
    private final Long current;

    /**
     * 每页数量
     */
    private final Long size;

    /**
     * 总数量
     */
    private Long total;

    /**
     * 当前页数据
     */
    private List<T> records;

    public <D> void construct(IPage<D> page, Class<T> tClass, Map<String, String> fieldMap) {
        this.total = page.getTotal();
        this.records = CollectionUtils.isEmpty(fieldMap)
                ? BeanCopyUtils.covertList(page.getRecords(), tClass)
                : BeanCopyUtils.covertList(page.getRecords(), tClass, fieldMap);
    }
}
