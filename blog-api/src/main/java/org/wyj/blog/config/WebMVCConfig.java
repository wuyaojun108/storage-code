package org.wyj.blog.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wyj.blog.handler.CrossDomainInterceptor;
import org.wyj.blog.handler.LoginInterceptor;

import javax.servlet.Filter;


/**
 * web配置
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;


    /**
     * 配置拦截器
     * @param registry 用于注册拦截器的实例
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义跨域配置拦截器，使用默认的跨域配置，无法在拦截器中生效，因为拦截器的调用在跨域配置之前
        // 所以自定义一个拦截器处理跨域请求，它在所有的拦截器之前调用，避免在其它拦截器中添加跨域逻辑
        registry.addInterceptor(crossDomainInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(loginInterceptor)
                // 配置登录拦截器，如果某些功能必须要登录之后才可以访问，则把它的路径写在这儿
                .addPathPatterns("/test")
                .addPathPatterns("/comment/create")
                .addPathPatterns("/comment/createReply")
                .addPathPatterns("/articles/publish");
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
