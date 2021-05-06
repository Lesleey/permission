package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.permission.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:56
 * @function
 */
@Mapper
public interface SysRoleMapper  extends BaseMapper<SysRole> {
    /**
     *   通过用户查询所有的角色信息
     * @param userid
     * @return
     */
    List<SysRole> getRolesByUserId(Integer userid);
}
