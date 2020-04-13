package com.filter;

import com.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 为所有工程统一加上try catch
 * @outhor Mr.JK
 * @create 2020-04-10  13:25
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();//提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAdnClose();//回滚事务
            e.printStackTrace();
            throw new RuntimeException(e);//把异常抛给Tomcat管理展示友好错误页面
        }
    }

    @Override
    public void destroy() {

    }
}
