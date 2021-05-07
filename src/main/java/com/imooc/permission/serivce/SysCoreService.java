package com.imooc.permission.serivce;

/**
 * @author Lesleey
 * @date 2021/5/7-20:42
 * @function
 */
public interface SysCoreService {
    /**
     *   判断当前用户是否具有访问该地址的权限
     * @param url
     * @return
     */
    boolean hasUrlAcl(String url);
}
