package com.common.utils.utils.common;

import java.io.Serializable;

/**
 * 分页 参数对象.
 *
 *
 */
@SuppressWarnings("serial")
public class Pageable implements Serializable {

    private static final int DEFAULT_PAGENUMBER = 1;

    private static final int DEFAULT_PAGESIZE = 20;

    private int pageNumber = DEFAULT_PAGENUMBER;

    private int pageSize = DEFAULT_PAGESIZE;

    /**
     * 分页参数对象.
     */
    public Pageable() {
    }

    /**
     * 构造函数，默认页码1， 页大小20.
     *
     * @param pageNumber
     *            页码
     * @param pageSize
     *            每页记录数
     */
    public Pageable(Integer pageNumber, Integer pageSize) {
        final int min = 1;
        final int max = 1000;
        if ((pageNumber != null) && (pageNumber.intValue() >= min)) {
            this.pageNumber = pageNumber.intValue();
        }
        if ((pageSize != null) && (pageSize.intValue() >= min) && (pageSize.intValue() <= max)) {
            this.pageSize = pageSize.intValue();
        }
        if (pageNumber == null) {
            this.pageNumber = Integer.valueOf(min);
        }
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    /**
     * 保存页码，如果<1，默认1.
     *
     * @param pageNumber
     */
    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置页大小，默认1-1000之间.
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        final int min = 1;
        final int max = 1000;
        if ((pageSize < min) || (pageSize > max)) {
            pageSize = DEFAULT_PAGESIZE;
        }
        this.pageSize = pageSize;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pageNumber;
        result = prime * result + pageSize;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Pageable other = (Pageable) obj;
        if (pageNumber != other.pageNumber) {
            return false;
        }

        if (pageSize != other.pageSize) {
            return false;
        }

        return true;
    }

}
