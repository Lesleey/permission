package com.imooc.permission.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.serivce.SysCoreService;
import com.imooc.permission.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Lesleey
 * @date 2021/5/7-20:33
 * @function
 */
@Component
@Order(Integer.MIN_VALUE + 1)
@Slf4j
public class AclControlFilter implements Filter {
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SysCoreService sysCoreService;


    private static final PathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String reqPath = request.getServletPath();
        if(shouldPass(reqPath)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Map<String, String[]> params = request.getParameterMap();
        SysUser sysUser = ContextUtil.loginUser();
        if(sysUser == null){
            log.error("用户没有登录，无法访问本系统！,访问地址 = {},参数 = {}", reqPath, objectMapper.writeValueAsString(params));
            noAuth(request, response);
            return;
        }

        if(!sysCoreService.hasUrlAcl(reqPath)){
            log.error("当前用户 = {} 没有访问该地址的权限, 访问地址 = {}, 参数 = {}", sysUser.getUsername(), reqPath, objectMapper.writeValueAsString(params));
            noAuth(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     *  用户没有权限的动作
     * @param request
     * @param response
     */
    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        // 如果是 接口请求
        if (!servletPath.contains(".")) {
            ResponseData jsonData = ResponseData.error("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(objectMapper.writeValueAsString(jsonData));
            return;
        } else {
            clientRedirect(authConfig.getNoAuthUrl(), response);
            return;
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    /**
     *  跳过白名单
     * @param url
     * @return
     */
    private boolean shouldPass(String url){
        List<String> whiteList = authConfig.getWhiteList();
        if(CollectionUtils.isNotEmpty(whiteList)){
            for (String whiteUrl : whiteList) {
                if(pathMatcher.match(whiteUrl, url))
                    return true;
            }
        }
        return false;
    }
}
