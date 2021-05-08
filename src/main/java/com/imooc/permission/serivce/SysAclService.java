package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysAcl;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysAclService extends IService<SysAcl> {

    /**
     *  根据权限模块移除所有的权限
     * @param aclModuleId
     * @return
     */
    Integer removeAclByAclModuleId(Integer aclModuleId);

    /**
     *  分页查询权限
     * @param pageNo
     * @param pageSize
     * @param aclModuleId
     * @return
     */
    Page<SysAcl> listPage(Integer pageNo, Integer pageSize, Integer aclModuleId);

    /**
     *   查询用户具有的权限信息
     * @param userid
     * @return
     */
    List<SysAcl> queryAclByUserId(Integer userid);

    /**
     *  查询角色具有的权限信息
     * @param roleIds
     * @return
     */
    List<SysAcl> queryAclByRoleId(List<Integer> roleIds);

    /**
     *  查询权限模块下的所有的有效的权限
     * @param aclModuleId
     * @return
     */
    List<SysAcl> listAclsByModuleId(Integer aclModuleId);

    /**
     *  回退操作
     * @param before
     * @param after
     * @return
     */
    Integer recover(SysAcl before, SysAcl after);


    /**
     *  查询该权限模块下具有的权限数量
     * @param id
     * @return
     */
    int countByAclModuleId(Integer id);
}
