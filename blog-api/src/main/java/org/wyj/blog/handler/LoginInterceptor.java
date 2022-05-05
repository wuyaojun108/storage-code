package org.wyj.blog.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.UserService;
import org.wyj.blog.utils.UserThreadLocal;
import org.wyj.blog.vo.ErrorCode;
import org.wyj.blog.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    /**
     * 登录拦截器
     * 1. 需要判断请求的接口路径是否为HandleMethod
     * 2. 判断token是否为空，如果为空，未登录
     * 3. 如果token不为空，登录验证。LoginService.checkToken
     * 4. 如果认证成功，放行即可
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response
            , Object handler) throws Exception {
        String token = request.getHeader("authorization");
        log.info("request uri: {}, request method: {}, token: {}"
                , request.getRequestURI(), request.getMethod(), token);
        // 如果用户的请求没有携带token，提醒用户没有登录
        if (token == null || "".equals(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode()
                    , ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        // 校验，是否可以从token中解析出用户ID，并且从redis中获取用户信息，如果用户携带的token不正确
        // 提醒用户登录
        Result userByToken = userService.findUserByToken(token);
        if (! userByToken.isSuccess()) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode()
                    , ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        // 登录验证成功，放行
        UserThreadLocal.put(userByToken.getData());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request
            , HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        UserThreadLocal.remove();
    }
}
