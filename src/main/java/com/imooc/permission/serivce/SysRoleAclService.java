package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysLog;
import com.imooc.permission.entity.SysRoleAcl;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysRoleAclService extends IService<SysRoleAcl> {
    /**
     *  修改角色对应的权限信息
     * @param roleId
     * @param aclIds
     * @return
     */
    int changeRoleAcl(Integer roleId, List<Integer> aclIds);

    int removeAclByRoleId(Integer roleId);
}
