package com.common.utils.memcached;

/**
 * 在锁内运行.
 *
 *
 * @param <T>
 *            返回的数据类型
 */
public interface WithinLockBlock<T> {

    /**
     * 从db加载数据，并保存到缓存.
     *
     * @return
     */
    T getDB();

    /**
     * 从缓存获取.
     *
     * @return
     */
    T getCache();
}
