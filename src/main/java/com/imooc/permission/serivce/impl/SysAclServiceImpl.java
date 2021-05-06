package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysAclMapper;
import com.imooc.permission.dao.SysAclModuleMapper;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysAclModule;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.serivce.SysAclModuleService;
import com.imooc.permission.serivce.SysAclService;
import com.imooc.permission.serivce.SysRoleService;
import com.imooc.permission.util.ContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysAclServiceImpl extends ServiceImpl<SysAclMapper, SysAcl>
 implements SysAclService {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Integer removeAclByAclModuleId(Integer aclModuleId) {
        return baseMapper.delete(new UpdateWrapper<SysAcl>().lambda().eq(SysAcl::getAclModuleId, aclModuleId));
    }

    @Override
    public Page<SysAcl> listPage(Integer pageNo, Integer pageSize, Integer aclModuleId) {
        Page<SysAcl> resPage = baseMapper.selectPage(new Page<SysAcl>(pageNo, pageSize), new QueryWrapper<SysAcl>().lambda()
                .eq(SysAcl::getAclModuleId, aclModuleId));
        return resPage;
    }

    @Override
    public List<SysAcl> queryAclByUserId(Integer userid) {
        if(StringUtils.equalsIgnoreCase(ContextUtil.loginUser().getUsername(), "lesleey")) {
            List<SysAcl> sysAcls = baseMapper.selectList(null);
            if(sysAcls == null) sysAcls = Collections.emptyList();
            return sysAcls;
        }
        List<SysRole> rolesByUserId = sysRoleService.getRolesByUserId(userid);
        if(CollectionUtils.isNotEmpty(rolesByUserId)){
            return queryAclByRoleId(rolesByUserId.stream().map(SysRole::getId).collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }

    @Override
    public List<SysAcl> queryAclByRoleId(List<Integer> roleIds) {
        if(CollectionUtils.isEmpty(roleIds))
            return Collections.emptyList();
        List<SysAcl> acls =  baseMapper.queryAclByRoleId(roleIds);
        if(acls == null)
            acls = Collections.emptyList();
        return acls;
    }

    @Override
    public List<SysAcl> listAclsByModuleId(Integer aclModuleId) {
        List<SysAcl> sysAcls = baseMapper.selectList(new QueryWrapper<SysAcl>().lambda().eq(SysAcl::getAclModuleId, aclModuleId).eq(SysAcl::getStatus, 1));
        if(sysAcls == null)
            sysAcls = Collections.emptyList();
        return sysAcls;
    }

}
