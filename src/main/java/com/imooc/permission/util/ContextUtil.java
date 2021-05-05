package com.imooc.permission.util;

import com.imooc.permission.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lesleey
 * @date 2021/5/4-21:40
 * @function
 */
public class ContextUtil {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    public static SysUser loginUser(){
        HttpServletRequest request = RequestUtil.getRequest();
        SysUser sysUser = userHolder.get();
        return sysUser;
    }

    public static void  add(SysUser sysUser){
        userHolder.set(sysUser);
    }

    public static void remove(){
        userHolder.remove();
    }
}
