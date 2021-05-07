package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleUserMapper;
import com.imooc.permission.dao.SysUserMapper;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.entity.vo.SysUserVo;
import com.imooc.permission.serivce.SysRoleUserService;
import com.imooc.permission.serivce.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
 implements SysUserService {


    @Override
    public SysUser selectSysUserByEmailOrTel(String principal) {
       return baseMapper.selectOne(new QueryWrapper<SysUser>().lambda()
        .eq(SysUser::getMail, principal).or().eq(SysUser::getTelephone, principal));
    }

    @Override
    public Page<SysUser> listPage(Integer pageNo, Integer pageSize, Integer deptId) {
        Page<SysUser> resPage = baseMapper.selectPage(new Page<SysUser>(pageNo, pageSize), new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getDeptId, deptId));
        return resPage;
    }

    @Override
    public List<SysUser> listUserOrderByDeptSeqAndUserSeq() {
        List<SysUser> users = baseMapper.listUserOrderByDeptSeqAndUserSeq();
        if(users == null)
            users = Collections.emptyList();
        return users;
    }

    @Override
    public List<SysUser> getUsersByRoleId(Integer roleId) {
        List<SysUser> users = baseMapper.getUsersByRoleId(roleId);
        if(users == null)
            users = Collections.emptyList();
        return users;
    }
}
