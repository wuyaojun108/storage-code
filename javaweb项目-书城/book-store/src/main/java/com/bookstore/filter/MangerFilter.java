package com.bookstore.filter;

import com.bookstore.beans.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 拦截所有后台管理相关的页面，用户必须登录才可以访问后台管理页面
 */
public class MangerFilter implements Filter {

    private Logger userLog = Logger.getLogger(MangerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUsername().equals("admin")) {
            // 是管理员，放行
            chain.doFilter(request, response);
        } else {
            // 不是管理员，返回首页
            httpServletResponse.sendRedirect("/book-store/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
