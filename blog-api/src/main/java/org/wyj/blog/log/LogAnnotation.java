package org.wyj.blog.log;

import java.lang.annotation.*;

/**
 * 用于记录日志的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operation() default "";
}
