package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysAcl;
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

    /**
     *  通过角色名删除对应的对应关系
     * @param roleId
     * @return
     */
    int removeAclByRoleId(Integer roleId);

    /**
     *  回退操作
     * @param roleId
     * @param before
     * @param after
     * @return
     */
    Integer recover(Integer roleId, List<SysRoleAcl> before, List<SysRoleAcl> after);

}
