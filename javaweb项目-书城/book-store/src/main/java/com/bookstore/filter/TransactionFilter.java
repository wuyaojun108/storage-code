package com.bookstore.filter;

import com.bookstore.utils.JDBCUtils;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 使用Filter过滤器，为所有的service方法统一添加事务管理
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
            JDBCUtils.commitAndClose();
        } catch (Exception e) {
            JDBCUtils.rollbackAndClose();
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {

    }
}
