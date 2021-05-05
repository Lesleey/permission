package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.entity.vo.SysUserVo;
import org.springframework.web.bind.annotation.PathVariable;

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
}
