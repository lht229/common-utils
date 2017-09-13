package com.common.utils.utils.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 返回到客户端的分页对象.
 *
 * @param <T>
 */
public class Page<T> {
    private final List<T> content = new ArrayList<T>();
    private final long totalPages;
    private final Pageable pageable;

    /**
     * 默认构建.
     */
    public Page() {
        this.totalPages = 0L;
        this.pageable = new Pageable();
    }

    /**
     * 分页对象.
     *
     * @param content
     *            返回前端的业务数据
     * @param total
     *            总记录数
     * @param pageable
     *            分页参数对象
     */
    public Page(List<T> content, long total, Pageable pageable) {
        this.content.addAll(content);
        this.totalPages = total;
        this.pageable = pageable;
    }

    public int getPageNumber() {
        return this.pageable.getPageNumber();
    }

    public int getPageSize() {
        return this.pageable.getPageSize();
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) getTotal() / getPageSize());
    }

    public List<T> getContent() {
        return this.content;
    }

    public long getTotal() {
        return this.totalPages;
    }

    public Pageable getPageable() {
        return this.pageable;
    }
}
