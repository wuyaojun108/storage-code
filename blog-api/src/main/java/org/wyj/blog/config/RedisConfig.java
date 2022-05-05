package org.wyj.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisClusterConfigProp prop;

    @Bean
    public JedisCluster getJedisCluster() {
        return new JedisCluster(getNodes(), prop.getTimeout(), poolConfig());
    }

    private JedisPoolConfig poolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(prop.getJedisPoolMaxIdle());
        config.setMinIdle(prop.getJedisPoolMinIdle());
        config.setMaxWaitMillis(prop.getJedisPoolMaxWait());
        return config;
    }

    private Set<HostAndPort> getNodes() {
        // 分割出集群节点
        String[] hp;
        Set<HostAndPort> nodes = new HashSet<>();
        for (String node : prop.getNodes()) {
            hp = node.split(":");
            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }
        return nodes;
    }
}
