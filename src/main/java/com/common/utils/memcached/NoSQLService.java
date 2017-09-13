package com.common.utils.memcached;

import java.io.Serializable;

import net.spy.memcached.CASValue;

/**
 * nosql统一接口.
 *
 *
 */
public interface NoSQLService {

    /**
     * 获取大的对象(经过压缩和分片存储到mc), 该方法必须和setLarge对应.
     *
     * @param key key
     * @param clazz 对象类型
     * @return 大对象
     */
    <T> T getLarge(String key, Class<T> clazz);

    /**
     * 保存大对象(经过压缩和分片存储到mc), 该方法必须和getLarge对应.
     *
     * @param key key
     * @param value 大对象
     * @param expireSecs 过期时间
     */
    <T> void setLarge(String key, T value, Integer expireSecs);

    /**
     * 删除大对象.
     *
     * @param key
     */
    void delLarge(String key);


    /**
     * 根据key获取数据.
     *
     * @param key
     * @return
     */
    <T extends Serializable> T getAs(String key);



    /**
     * 设置key, value.
     *
     * @param key
     * @param value
     */
    <T extends Serializable> void setAs(String key, T value);

    /**
     * 根据key获取数据.
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 保存数据，带超时时间，单位秒.
     *
     * @param key
     * @param value
     * @param expireSecs
     */
    void setex(String key, String value, Integer expireSecs);

    /**
     * 删除key.
     *
     * @param key
     */
    void del(String key);

    /**
     * 设置key, value.
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 保存数据，带超时时间，单位秒. 如果要防止覆盖，请用<T> void cas(String key, T value, Integer
     * expireSecs, long version)
     *
     * @param key
     * @param value
     * @param expireSecs
     */
    <T> void set(String key, T value, Integer expireSecs);

    /**
     * 根据key获取数据.
     *
     * @param key
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 带版本号的保存，防止同步修改覆盖，和<T> CASValue<T> gets(String key)一起用. 1. 如果没有缓存，则新建 <br>
     * 2. 如果已经存在， 版本号不一致， 则抛出OptimisticLockException异常<br>
     * 3. 如果更新失败，抛出BusinessException<br>
     * 4. 更新成功，顺利返回<br>
     *
     * @param key
     * @param value
     * @param expireSecs
     *            过期秒
     * @param version
     *            版本号 从gets返回
     * @exception 会抛出OptimisticLockException
     */
    <T> void cas(String key, T value, Integer expireSecs, long version);

    /**
     * 获取对象，并且返回版本号.
     *
     * @param key
     * @return
     */
    <T> CASValue<T> gets(String key);

    /**
     * 原子化的增长接口.
     *
     * @param key
     * @param increaseBy
     *            增加的数量
     * @param expireSecs
     *            过期时间，注意这个过期时间，在此调用incr, 不会更新
     * @return 返回增加之后的值
     */
    long incr(String key, int increaseBy, int expireSecs);

    /**
     * mc锁.
     *
     * @param key
     *            key
     * @return action 执行的业务代码
     */
    <T> T get(String key, WithinLockBlock<T> action);

    /**
     * 获取并且重置过期时间.
     *
     * @param key key
     * @return 返回的数据
     */
    String getAndTouch(String key,  int expireSecs);

}
