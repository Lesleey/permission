package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleUser;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysRoleUserService extends IService<SysRoleUser> {
    /**
     *   删除 roleId 对应的角色用户关系
     * @param roleId
     * @return
     */
    Integer removeRoleUserByRoleId(Integer roleId);

    /**
     *  修改角色用户对应关系
     * @param userIds
     * @param roleId
     * @return
     */
    Integer changeRoleUsers(List<Integer> userIds, Integer roleId);

    /**
     *  检测用户是否具有角色
     * @param roleId
     * @param userId
     * @return
     */
    boolean isHasRole(Integer roleId, Integer userId);

    /**
     *  删除用户对应的角色信息
     * @param userid
     * @return
     */
    boolean removeByUserId(Integer userid);

    /**
     *  回退操作
     * @param userid
     * @param before
     * @param after
     * @return
     */
    boolean recover(int userid, List<SysRoleUser> before, List<SysRoleUser> after);
}
