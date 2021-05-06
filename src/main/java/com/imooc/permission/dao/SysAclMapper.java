package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.permission.entity.SysAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:54
 * @function
 */
@Mapper
public interface SysAclMapper  extends BaseMapper<SysAcl> {

    /**
     *  通过角色id查询所有的权限信息
     * @param roleIds
     * @return
     */
    List<SysAcl> queryAclByRoleId(@Param("roleIds") List<Integer> roleIds);
}
