package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleAclMapper;
import com.imooc.permission.dao.SysRoleMapper;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleAcl;
import com.imooc.permission.serivce.SysRoleAclService;
import com.imooc.permission.serivce.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
 implements SysRoleService {

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Override
    public List<SysRole> getRolesByUserId(Integer userid) {
        List<SysRole> roles = baseMapper.getRolesByUserId(userid);
        if(roles == null)
            roles = Collections.emptyList();
        return roles;
    }

    @Override
    public Integer recover(SysRole before, SysRole after) {
        if (before == null) {
            removeRoleById(after.getId());
        } else if (after == null){
            validRoleName(before.getName());
            save(before);
        }else {
            if(!StringUtils.equalsIgnoreCase(before.getName(), after.getName()))
                validRoleName(before.getName());
            updateById(before);
        }
        return 1;
    }

    @Override
    public Integer removeRoleById(Integer roleId) {
        sysRoleAclService.removeAclByRoleId(roleId);
        removeById(roleId);
        return 1;
    }

    private void validRoleName(String name){
        if(count(new QueryWrapper<SysRole>().lambda()
                .eq(SysRole::getName, name)) > 0)
            throw new RuntimeException("角色名称已存在！");
    }
}
