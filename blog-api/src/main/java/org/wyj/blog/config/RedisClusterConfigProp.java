package org.wyj.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisClusterConfigProp {
    private List<String> nodes;
    private Integer jedisPoolMaxActive;
    private Integer jedisPoolMaxWait;
    private Integer jedisPoolMaxIdle;
    private Integer jedisPoolMinIdle;
    private Integer timeout;
    private Integer maxAttempts;

}
