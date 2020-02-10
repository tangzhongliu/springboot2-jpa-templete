package com.lemon.templete.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * @author 汤中流
 * @date 2019/07/17
 */
@WebFilter(filterName = "commonFilter", urlPatterns = "/*")
public class CommonFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("--------------->>> CommonFilter 初始化");
    }

    @Override
    public void destroy() {
        logger.info("--------------->>> CommonFilter 销毁");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 下面代码块，解决该系统的swagger3主页加载自定义json接口文件(static/swagger3/swagger-api.json)的跨域问题。
        // ################## 解决跨域 begin ##################
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        // ################## 解决跨域 end ##################
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("--------------->>> CommonFilter doFilter过滤");
    }
}
