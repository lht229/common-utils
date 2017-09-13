package com.common.utils.memcached;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.utils.utils.common.ResponseCode;
import com.common.utils.utils.common.exception.BusinessException;
import com.common.utils.utils.common.exception.OptimisticLockException;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * 缓存服务，memcached.
 *
 *
 */
//@Component
//@ConfigurationProperties(prefix = "MemcachedService")
public class MemcachedClientUtils extends MemcachedClientFactoryBean implements NoSQLService {

    /**
     * 分割的缓存大小.
     */
    public static final Integer SURVEY_CACHE_SPILT_SIZE = 1024 * 64 * 13;

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedClientUtils.class);

    private static final long FAKE_VERSION = -1;

    private MemcachedClient getClient() {
        try {
            MemcachedClient client = (MemcachedClient) super.getObject();
            return client;
        } catch (Exception e) {
            LOGGER.error("memcached 无法连接", e);
            return null;
        }
    }

    /**
     * 根据key获取数据.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T getAs(String key) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        return (T) client.get(key);
    }

    /**
     * 保存数据到cache, 同步方式.
     */
    @Override
    public <T extends Serializable> void setAs(String key, T value) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        final int expire5Day = 60 * 30;
        final int timeout = 45;
        OperationFuture<Boolean> future = client.set(key, expire5Day, value);
        try {
            future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            LOGGER.error("mc保存数据错误:", e);
        }
    }

    /**
     * 根据key获取数据.
     */
    @Override
    public String get(String key) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        return (String) client.get(key);
    }

    /**
     * 设置超时.
     */
    @Override
    public void setex(String key, String value, Integer seconds) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        client.set(key, seconds, value);

    }

    /**
     * 删除key, 注意这里是异步操作.
     */
    @Override
    public void del(String key) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        client.delete(key);

    }

    /**
     * 保存数据到cache, 同步方式.
     */
    @Override
    public void set(String key, String value) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        final int expire5Day = 60 * 30;
        final int timeout = 45;
        OperationFuture<Boolean> future = client.set(key, expire5Day, value);
        try {
            future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            LOGGER.error("mc保存数据错误:", e);
        }

    }

    /**
     * 根据key获取数据, 返回序列化之后的对象，目前使用JSON序列化.
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        String json = get(key);
        if (json == null) {
            return null;
        }
        // return JsonUtil.fromJson(json, clazz);
        try {
            return JsonUtil.fromJson(json, clazz);
        } catch (IOException e) {
            LOGGER.error("MemcachedService --> get :", e);
        }
        return null;
    }

    /**
     * 保存数据到cache, 目前使用JSON序列化.
     */
    @Override
    public <T> void set(String key, T value, Integer expireSecs) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        // client.set(key, expireSecs, JsonUtil.toJson(value));

        try {
            OperationFuture<Boolean> future = client.set(key, expireSecs, JsonUtil.toJson(value));
            final int timeout = 45;
            try {
                future.get(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                LOGGER.error("mc保存数据错误:", e);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("MemcachedService --> set :", e);
        }
    }


    /**
     * 更新缓存, 带版本号，防止同步修改覆盖.
     *
     */
    @Override
    public <T> void cas(String key, T value, Integer expireSecs, long version) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        if (version == FAKE_VERSION) {
            set(key, value, expireSecs);
            return;
        }

        // CASResponse response = client.cas(key, version, expireSecs,
        // JsonUtil.toJson(value));
        CASResponse response = null;
        try {
            if (value instanceof String || value instanceof Number) {
                response = client.cas(key, version, expireSecs, value);
            } else {
                response = client.cas(key, version, expireSecs, JsonUtil.toJson(value));
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("cas错误:" + response);
            throw new BusinessException(ResponseCode.ERROR, "系统错误，稍后再试。");
        }

        if (response == null) {
            throw new BusinessException(ResponseCode.ERROR, " response 系统错误，稍后再试。");
        }
        if (response == CASResponse.OK) {
            return;
        }
        if (response == CASResponse.NOT_FOUND) {
            set(key, value, expireSecs);
            return;
        }
        if (response == CASResponse.EXISTS) {
            throw new OptimisticLockException(ResponseCode.BAD_REQUEST, "数据已经被其他人修改，请重新编辑。");
        }
        if (response == CASResponse.OBSERVE_ERROR_IN_ARGS || response == CASResponse.OBSERVE_TIMEOUT) {
            LOGGER.error("cas错误:" + response);
            throw new BusinessException(ResponseCode.ERROR, "系统错误，稍后再试。");
        }

    }

    /**
     * 从缓存提取数据,带版本号, 如果没有缓存，则返回CASValue的 value为null.
     */
    @Override
    public <T> CASValue<T> gets(String key) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        net.spy.memcached.CASValue<T> value = (net.spy.memcached.CASValue<T>) client.gets(key);
        if (value == null) {
            return new CASValue<T>(FAKE_VERSION, null);
        }
        return new CASValue<T>(value.getCas(), value.getValue());
    }

    /**
     * 计数接口.
     */
    @Override
    public long incr(String key, int increaseBy, int expireSecs) {
        MemcachedClient client = getClient();
        if (client == null) {
            return 0;
        }
        return client.incr(key, increaseBy, increaseBy, expireSecs);
    }

    /**
     * mc锁.
     */
    @Override
    public <T> T get(String key, WithinLockBlock<T> action) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        final int retryCount = 30;
        final int lockSec = 60 * 5;
        final int ignoreCount = 5;
        final int initInterval = 20;
        final int step = 50;
        String mutexKey = key + "_mutex";
        Boolean unlocked = false;
        try {
            for (int i = 0; i < retryCount; i++) {
                T v = action.getCache();
                if (v != null) {
                    LOGGER.info("mc缓存锁命中,mutexKey={}, i={}", mutexKey, i);
                    return v;
                }
                OperationFuture<Boolean> future = client.add(mutexKey, lockSec, FAKE_VERSION);
                try {
                    unlocked = future.get();
                    if (unlocked) {
                        return action.getDB();
                    } else {
                        if (i > ignoreCount) {
                            LOGGER.info("mc缓存锁竞争，无法获取锁,mutexKey={}, 重试={}", mutexKey, i + 1);
                            try {
                                Thread.sleep(initInterval + (i - ignoreCount + 1) * step);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        continue;
                    }
                } catch (Exception e) {
                    LOGGER.error("getWithLock错误:", e);
                    return null;
                }

            }
            LOGGER.warn("mc retry 50 times, fetch from db,mutexKey={}", mutexKey);
            return action.getDB();

        } finally {
            if (unlocked) {
                client.delete(mutexKey);
            }
        }

    }


    /**
     * 删除大对象, 如果第一块存在, 继续删除其他.
     */
    @Override
    public void delLarge(String originalKey) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        byte[] firstChunk = (byte[]) client.get(originalKey + "_chunk0");
        if (firstChunk == null) {
            return;
        }



        byte chunkCount = firstChunk[0];
        List<OperationFuture<Boolean>> futureList = new ArrayList<OperationFuture<Boolean>>(chunkCount);

        for (byte nextChunkIndex = 0; nextChunkIndex < chunkCount; nextChunkIndex++) {
            futureList.add(client.delete(originalKey + "_chunk" + nextChunkIndex));
        }

        final int timeout = 45;

        for (OperationFuture<Boolean> future : futureList) {
            try {
                future.get(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                LOGGER.error("mc保存数据错误, key='" + originalKey + "'", e);
            }
        }
    }

    /**
     * 从mc获取大对象, 先合并，在解压.
     */
    @Override
    public <T> T getLarge(String originalKey, Class<T> clazz) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        StopWatch watch = new StopWatch();
        watch.start();
        byte[] firstChunk = (byte[]) client.get(originalKey + "_chunk0");
        if (firstChunk == null) {
            LOGGER.info("key='" + originalKey + "_chunk0' is empty");
            return null;
        }
        List<byte[]> compressedList = new ArrayList<byte[]>();
        compressedList.add(firstChunk);
        // 一共分几片
        byte chunkCount = firstChunk[0];
        for (byte nextChunkIndex = 1; nextChunkIndex < chunkCount; nextChunkIndex++) {
            byte[] chunk = (byte[]) client.get(originalKey + "_chunk" + nextChunkIndex);
            if (chunk == null) {
                LOGGER.error("key='" + originalKey + "_chunk'" + nextChunkIndex + " is empty");
                return null;
            }
            compressedList.add(chunk);
        }
        watch.stop();
        LOGGER.info("key='" + originalKey + "' get chunks costs:" + watch.getTime());

        try {
            watch.reset();
            watch.start();
            byte[] decompressed = LZ4Compress.decompress(ByteArrayUtils.merge(compressedList.toArray(new byte[0][0])));
            LOGGER.info("key='" + originalKey + "' decompressed&merge chunks costs:" + watch.getTime());
            watch.stop();

            watch.reset();
            watch.start();
            T value = JsonUtil.fromJsonbytes(decompressed, clazz);
            watch.stop();
            LOGGER.info("key='" + originalKey + "' from jsonbytes costs:" + watch.getTime());
            return value;
        } catch (IOException e) {
            LOGGER.error("key='" + originalKey + " decompress error", e);
            return null;
        }
    }

    /**
     * 把大对象保存到mc, 先压缩, 再分片.<br>
     * 同步方式.
     */
    @Override
    public <T> void setLarge(String originalKey, T value, Integer expireSecs) {
        MemcachedClient client = getClient();
        if (client == null) {
            return;
        }
        if (value == null) {
            LOGGER.warn("value is null, key='" + originalKey + "'");
            return;
        }
        byte[] original = null;
        try {
            original = JsonUtil.toJsonbytes(value);
        } catch (JsonProcessingException e1) {
            LOGGER.error("can't parse to json, key='" + originalKey + "'");
            return;
        }

        StopWatch watch = new StopWatch();
        watch.start();
        List<byte[]> chunks = null;
        try {
            byte[] compressed = LZ4Compress.compress(original);
            chunks = ByteArrayUtils.split(compressed, SURVEY_CACHE_SPILT_SIZE);
            final int unit = 1024;
            final int hundred = 100;
            LOGGER.info("key='{}', 压缩前={}K, 压缩后={}K, 切分块={}, 压缩比率={}%", originalKey, original.length / unit,
                    compressed.length / unit, chunks.size(),
                    compressed.length * hundred / Float.valueOf(original.length));
        } catch (IOException e1) {
            LOGGER.error("can't split&compress json, key='" + originalKey + "'");
            return;
        }
        watch.stop();
        LOGGER.info("key='" + originalKey + "' compress costs:" + watch.getTime());

        if (chunks.size() == 0) {
            LOGGER.error("can't split json, key='" + originalKey + "'");
            return;
        }

        watch.reset();
        watch.start();
        List<OperationFuture<Boolean>> futureList = new ArrayList<OperationFuture<Boolean>>(chunks.size());

        for (int i = 0, len = chunks.size(); i < len; i++) {
            String suffix = "_chunk" + i;
            String key = originalKey + suffix;
            futureList.add(client.set(key, expireSecs, chunks.get(i)));
        }


        final int timeout = 45;

        for (OperationFuture<Boolean> future : futureList) {
            try {
                future.get(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                LOGGER.error("mc保存数据错误, key='" + originalKey + "'", e);
            }
        }
        watch.stop();
        LOGGER.info("key='" + originalKey + "' save chunks to mc costs:" + watch.getTime());
        chunks = null;

    }

    /**
     * 获取并且重置过期时间, 目前服务端mc不支持??.
     */
    @Override
    public String getAndTouch(String key, int expireSecs) {
        MemcachedClient client = getClient();
        if (client == null) {
            return null;
        }
        String value = get(key);
        if (value != null) {
            client.set(key, expireSecs, value);
        }
        return value;
    }


}
