package org.wyj.blog.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.config.RedisClusterConfigProp;
import redis.clients.jedis.JedisCluster;

public class RedisTest extends BaseTest {

    @Autowired
    private RedisClusterConfigProp redisClusterConfigProp;

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void test1(){
        System.out.println("redisClusterConfigProp = " + redisClusterConfigProp);
    }

    @Test
    public void test2(){
        jedisCluster.set("aa", "bb");
    }


}
