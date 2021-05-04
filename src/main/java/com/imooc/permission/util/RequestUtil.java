package com.imooc.permission.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lesleey
 * @date 2021/5/4-21:39
 * @function
 */
public class RequestUtil {
    /**
     * 获取request对象
     * @return request
     */
    public static HttpServletRequest getRequest() {
        try {
            return((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取客户端请求的路径名，如：/object/delObject
     * @return String
     */
    public static String getServletPath() {
        try {
            return // 项目的真实路径
                    getRequest().getServletPath();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取服务器地址，如：localhost
     * @return String
     */
    public static String getServerName() {
        try {
            return // 项目的真实路径
                    getRequest().getServerName();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获用户地址，如：127.0.0.1
     * @return String
     */
    public static String getRemoteAddr() {
        try {
            String remoteAddr = getRequest().getHeader("X-Real-IP");
            if (StringUtils.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("X-Forwarded-For");
            } else if (StringUtils.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("Proxy-Client-IP");
            } else if (StringUtils.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("WL-Proxy-Client-IP");
            }
            return remoteAddr != null ? remoteAddr : getRequest().getRemoteAddr();
        } catch (Exception e) {
            return "";
        }
    }
}
