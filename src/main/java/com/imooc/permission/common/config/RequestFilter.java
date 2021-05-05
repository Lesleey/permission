package com.imooc.permission.common.config;

import com.alibaba.druid.util.StringUtils;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.util.ContextUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Lesleey
 * @date 2021/5/5-16:00
 * @function
 */
@Component
public class RequestFilter implements Filter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        ContextUtil.add((SysUser) request.getSession().getAttribute("user"));
        if((servletPath.contains(".")  && !servletPath.endsWith("jsp") )|| pathMatcher.match(servletPath, "/signin.jsp")
                || pathMatcher.match("/user/login", servletPath) || ContextUtil.loginUser() != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            ContextUtil.remove();
        }else{
            ((HttpServletResponse) servletResponse).sendRedirect("/signin.jsp");
        }
    }
}
