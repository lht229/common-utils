package com.common.utils.redis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

/**
 * redis.
 *
 * @author:haitao.liu
 *
 */
public class RedisJava {

//     public static void main(String[] args) throws InterruptedException {
//         // 连接本地的 Redis 服务
//         // Jedis jedis = new Jedis("192.168.1.178", 6379);
//         Jedis jedis = RedisUtil.getJedis();
//         System.out.println("Connection to server sucessfully");
//         // 查看服务是否运行
//         System.out.println("Server is running: " + jedis.ping());
//         RedisJava testRedis = new RedisJava();

//         testRedis.testString(jedis);
//         testRedis.testMap(jedis);
//         // testRedis.testList(jedis);
//         // testRedis.testSet(jedis);
//         // testRedis.test(jedis);
//         // testRedis.testRedisPool(jedis);
//     }

    /**
     * redis存储字符串
     */
    public void testString(Jedis jedis) {
        // -----添加数据----------
        jedis.set("name", "xinxin");// 向key-->name中放入了value-->xinxin
        System.out.println(jedis.get("name"));// 执行结果：xinxin

        jedis.append("name", " is my lover"); // 拼接
        System.out.println(jedis.get("name"));

        jedis.del("name"); // 删除某个键
        System.out.println(jedis.get("name"));
        // 设置多个键值对
        jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
        jedis.incr("age"); // 进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-"
                + jedis.get("qq"));

    }

    /**
     * redis操作Map
     */
    public void testMap(Jedis jedis) {
        jedis.del("name"); // 删除某个键
        jedis.del("age"); // 删除某个键
        jedis.del("qq"); // 删除某个键
        jedis.del("user"); // 删除某个键
        // -----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user", map);
        // 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        // 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        // 删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
        System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     */
    public void testList(Jedis jedis) {
        // 开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        // 先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        // 再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     */
    public void testSet(Jedis jedis) {
        jedis.del("user");
        // 添加
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user", "ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");
        // 移除noname
        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));// 获取所有加入的value
        System.out.println(jedis.sismember("user", "who"));// 判断 who
                                                           // 是否是user集合的元素
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));// 返回集合的元素个数
    }

    public void test(Jedis jedis) throws InterruptedException {
        // jedis 排序
        // 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");// 先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果
        System.out.println(jedis.lrange("a", 0, -1));
    }

    public void testRedisPool(Jedis jedis) {
        jedis.set("newname", "中文测试");
        System.out.println(jedis.get("newname"));
        System.out.println(jedis.ttl("newname"));
    }

    /**
     * 事务方式(Transactions)
     *
     * 我们调用jedis.watch(…)方法来监控key，如果调用后key值发生变化，则整个事务会执行失败。
     * 另外，事务中某个操作失败，并不会回滚其他操作。这一点需要注意。还有，我们可以使用discard()方法来取消事务。
     *
     * @throws
     */

    public void test2Trans(Jedis jedis) {
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
        for (int i = 0; i < 100000; i++) {
            tx.set("t" + i, "t" + i);
        }
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start) / 1000.0)
                + " seconds");
        jedis.disconnect();
    }

    /**
     * 管道(Pipelining)
     * 有时，我们需要采用异步方式，一次发送多个指令，不同步等待其返回结果。这样可以取得非常好的执行效率。这就是管道，调用方法如下：
     *
     * @param jedis
     * @throws
     */

    public void test3Pipelined(Jedis jedis) {
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start) / 1000.0)
                + " seconds");
        jedis.disconnect();
    }

    /**
     * 管道中调用事务
     * 就Jedis提供的方法而言，是可以做到在管道中使用事务，其代码如下：
     * 但是经测试（见本文后续部分），发现其效率和单独使用事务差不多，甚至还略微差点。
     *
     * @throws
     */
    public void test4combPipelineTrans(Jedis jedis) {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("" + i, "" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start) / 1000.0)
                + " seconds");
        jedis.disconnect();
    }

    /**
     * 分布式直连同步调用
     * 并且是同步调用，每步执行都返回执行结果。类似地，还有异步管道调用
     *
     * @param jedis
     * @throws
     */
    public void test5shardNormal(Jedis jedis) {
        List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
                "192.168.1.178", 6379), new JedisShardInfo("192.168.1.178",
                6380));

        ShardedJedis sharding = new ShardedJedis(shards);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = sharding.set("sn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0)
                + " seconds");

        sharding.disconnect();
    }

    /**
     * 分布式直连异步调用
     *
     * @throws
     */
    public void test6shardpipelined() {
        List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
                "localhost", 6379), new JedisShardInfo("localhost", 6380));

        ShardedJedis sharding = new ShardedJedis(shards);

        ShardedJedisPipeline pipeline = sharding.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sp" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined@Sharing SET: " + ((end - start) / 1000.0)
                + " seconds");

        sharding.disconnect();
    }

    /**
     * 如果，你的分布式调用代码是运行在线程中，那么上面两个直连调用方式就不合适了，因为直连方式是非线程安全的，这个时候，你就必须选择连接池调用。
     *
     * @throws
     */
    public void test7shardSimplePool() {
        List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
                "localhost", 6379), new JedisShardInfo("localhost", 6380));

        ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),
                shards);

        ShardedJedis one = pool.getResource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = one.set("spn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0)
                + " seconds");

        pool.destroy();
    }

    /**
     * 分布式连接池异步调用
     *
     * @throws
     */
    public void test8shardPipelinedPool() {
        List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
                "localhost", 6379), new JedisShardInfo("localhost", 6380));

        ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),
                shards);

        ShardedJedis one = pool.getResource();

        ShardedJedisPipeline pipeline = one.pipelined();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start) / 1000.0)
                + " seconds");
        pool.destroy();
    }
}
