package org.wyj.blog.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.wyj.blog.vo.ArticleMessage;

import java.util.Set;

// 监听topic中的消息
@Component
@Slf4j
@RocketMQMessageListener(topic = "spring-boot-test1", consumerGroup = "blog-api-g1")
public class ArticleListener implements RocketMQListener<ArticleMessage> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(ArticleMessage message) {
        Integer offset = message.getOffset();
        log.info("rocketmq 队列收到消息：offset = " + offset);
        String pattern =
                "*listArticles::\\[PageParams\\(offset=" + offset + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        // 清除符合条件的缓存
        if(keys != null){
            for (String key : keys) {
                log.info("更新缓存，删除键：" + key);
                redisTemplate.delete(key);
            }
        }
    }
}
