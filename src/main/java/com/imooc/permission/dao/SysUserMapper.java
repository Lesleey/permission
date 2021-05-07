package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.entity.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:42
 * @function
 */
@Mapper
public interface SysUserMapper  extends BaseMapper<SysUser> {


    /**
     *  按照排序返回所有的用户数据
     * @return
     */
    List<SysUser> listUserOrderByDeptSeqAndUserSeq();

    /**
     *   通过角色id 获取所有的用户信息
     * @param roleId
     * @return
     */
    List<SysUser> getUsersByRoleId(Integer roleId);
}
