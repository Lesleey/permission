package com.imooc.permission.util;

import com.imooc.permission.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lesleey
 * @date 2021/5/4-21:40
 * @function
 */
public class ContextUtil {
    private static SysUser ANNO = null;
    static {
        ANNO = new SysUser();
        ANNO.setId(-1);
        ANNO.setUsername("anno");
    }
    public static SysUser loginUser(){
        HttpServletRequest request = RequestUtil.getRequest();
        return null;
    }
}
