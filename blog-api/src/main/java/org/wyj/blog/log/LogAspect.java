package org.wyj.blog.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.wyj.blog.utils.HttpContextUtils;
import org.wyj.blog.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(org.wyj.blog.log.LogAnnotation)")
    public void pt() {}

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executeTime = System.currentTimeMillis() - start;
        recordLog(joinPoint, executeTime);
        return result;
    }

    // 执行记录日志的操作
    private void recordLog(ProceedingJoinPoint joinPoint, long executeTime) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String fullMethodName = className + "." + methodName;
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String requestURI = request.getRequestURI();
        // 记录日志
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("module:{}, operation:{}, executeMethod: {}, ip:{}, uri: {}" +
                        ", params:{}, execute time: {} ms"
                , logAnnotation.module()
                , logAnnotation.operation()
                , fullMethodName
                , IpUtils.getIpAddress(request)
                , requestURI
                , params
                , executeTime);
    }
}
