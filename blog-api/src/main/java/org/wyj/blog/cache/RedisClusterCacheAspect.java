package org.wyj.blog.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wyj.blog.vo.Result;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RedisClusterCacheAspect {

    @Autowired
    private JedisCluster jedisCluster;

    @Pointcut("@annotation(org.wyj.blog.cache.Cache)")
    public void pt() {}

    @Around("pt()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            // 打印连接点的详细信息
            // joinPointDetail(joinPoint);

            // 获取目标类的class对象
            Class<?> targetClass = joinPoint.getTarget().getClass();
            // 获取连接点方法的签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取连接点方法的参数
            Object[] args = joinPoint.getArgs();
            // 获取连接点方法的类对象
            Method method = signature.getMethod();
            // 获取连接点的注解
            Cache annotation = method.getAnnotation(Cache.class);
            long expire = annotation.expire();
            String cacheName = annotation.name();

            // 拼接类名、方法名、参数，作为redis的键
            String className = targetClass.getName();
            String methodName = method.getName();
            String paramStr = Arrays.toString(args);
            String redisKey = cacheName + "::" + className + "::"
                    + methodName + "::" + paramStr;

            // 根据redis键，从redis中取值
            String redisValue = jedisCluster.get(redisKey);
            if (redisValue != null && redisValue.length() > 0) {
                log.info("走了缓存~~~: {}.{}({})", className, methodName, paramStr);
                return JSON.parseObject(redisValue, Result.class);
            }
            // 执行连接点的方法
            Object proceed;
            if (args.length > 0) {
                proceed = joinPoint.proceed(args);
            } else {
                proceed = joinPoint.proceed();
            }
//            jedisCluster.set(redisKey, JSON.toJSONString(proceed)
//                    , Duration.ofMillis(expire));
            jedisCluster.setex(redisKey, (int)(expire / 1000)
                    , JSON.toJSONString(proceed));
            log.info("存入缓存~~~: {}.{}({})", className, methodName, paramStr);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return Result.fail(-999, "系统错误");
    }

    // 打印连接点的详细信息
    private void joinPointDetail(ProceedingJoinPoint joinPoint) {
        System.out.println("joinPoint.getSignature() = " + joinPoint.getSignature());
        System.out.println("joinPoint.getKind() = " + joinPoint.getKind());
        System.out.println("joinPoint.toShortString() = " + joinPoint.toShortString());
        System.out.println("joinPoint.toLongString() = " + joinPoint.toLongString());
        System.out.println("joinPoint.getTarget() = " + joinPoint.getTarget());
        System.out.println("joinPoint.getThis() = " + joinPoint.getThis());
        System.out.println("joinPoint.getArgs() = " + Arrays.toString(joinPoint.getArgs()));
    }
}
