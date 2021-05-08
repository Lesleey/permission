package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.*;
import com.imooc.permission.entity.param.SearchLogParam;

import java.io.IOException;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysLogService extends IService<SysLog> {

    Integer saveDeptLog(SysDept before, SysDept after);

    Integer saveUserLog(SysUser before, SysUser after);

    Integer saveAclModule(SysAclModule before, SysAclModule AFTER);

    Integer saveAclLog(SysAcl before, SysAcl after);

    Integer saveRole(SysRole before, SysRole after);

    Integer saveRoleAcl(int roleId, List<SysRoleAcl> before, List<SysRoleAcl> after);

    Integer saveRoleUser(Integer roleId, List<SysRoleUser> before, List<SysRoleUser> after);


    /**
     *  分页查询日志
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SysLog> listPage(Integer pageNo, Integer pageSize, SearchLogParam param);

    /**
     *  回退某条日志
     * @param logId
     * @return
     */
    Integer coverByLogId(Integer logId) throws IOException;
}
