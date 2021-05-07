package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.entity.vo.SysUserVo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysUserService extends IService<SysUser> {
    /**
     *   通过邮箱或者手机号查询 用户
     * @param principal
     * @return
     */
    SysUser selectSysUserByEmailOrTel(String principal);

    /**
     *  分页查询
     * @return
     */
    Page<SysUser> listPage(Integer pageNo, Integer pageSize, Integer deptId);


    /**
     *  先按照部门顺序、再按照人员顺序返回所有的用户信息
     * @return
     */
    List<SysUser> listUserOrderByDeptSeqAndUserSeq();

    /**
     *   通过角色id获取所有的用户
     * @param roleId
     * @return
     */
    List<SysUser> getUsersByRoleId(Integer roleId);
}
