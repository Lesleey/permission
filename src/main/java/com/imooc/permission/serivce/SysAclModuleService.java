package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysAclModule;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysAclModuleService extends IService<SysAclModule> {

    /**
     *   更新权限模块
     * @param sysAclModule
     */
    boolean updateSysAclModule(SysAclModule sysAclModule);

    /**
     *  按照排序查询所有的权限模块
     * @return
     */
    List<SysAclModule> listOrderByLevelAndSn();
}
