package com.tianhui.zhishu.MemcachedTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.utils.memcached.MemcachedClientUtils;
import com.common.utils.memcached.NoSQLService;
import com.tianhui.zhishu.SpringBootTestCaseBase;

/**
 *
 * Memcached测试.
 *
 * @author:haitao.liu
 *
 */
public class MemcachedTestCase extends SpringBootTestCaseBase {

    @Autowired
    private MemcachedClientUtils memcachedClientUtils;

    @Autowired
    private NoSQLService noSQLService;

    @Test
    public void testJsonUtil() {
        noSQLService.set("aaa", "bbbb");
        System.err.println(noSQLService.get("aaa") + "---------------");

    }
}