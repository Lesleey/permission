package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleAcl;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     *    查询该用户具有的所有的角色信息
     * @param userid
     * @return
     */
    List<SysRole> getRolesByUserId(Integer userid);


    /**
     *  回退操作
     * @param before
     * @param after
     * @return
     */
    Integer recover(SysRole before, SysRole after);

    /**
     *  通过角色id删除对应的信息
     * @param roleId
     * @return
     */
    Integer removeRoleById(Integer roleId);
}
