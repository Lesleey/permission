package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysLogMapper;
import com.imooc.permission.dao.SysRoleAclMapper;
import com.imooc.permission.entity.SysLog;
import com.imooc.permission.entity.SysRoleAcl;
import com.imooc.permission.serivce.SysLogService;
import com.imooc.permission.serivce.SysRoleAclService;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysRoleAclServiceImpl extends ServiceImpl<SysRoleAclMapper, SysRoleAcl>
 implements SysRoleAclService {
    @Override
    public int changeRoleAcl(Integer roleId, List<Integer> aclIds) {
        int row = removeAclByRoleId(roleId);
        if(CollectionUtils.isNotEmpty(aclIds)){
            saveBatch(
                aclIds.stream().map(acId ->{
                    SysRoleAcl sysRoleAcl = new SysRoleAcl();
                    sysRoleAcl.setRoleId(roleId);
                    sysRoleAcl.setAclId(acId);
                    sysRoleAcl.setOperateTime(new Date());
                    sysRoleAcl.setOperateIp(RequestUtil.getRemoteAddr());
                    sysRoleAcl.setOperator(ContextUtil.loginUser().getUsername());
                    return sysRoleAcl;
                }).collect(Collectors.toList())
            );

        }
        return row;
    }

    @Override
    public int removeAclByRoleId(Integer roleId) {
        return baseMapper.delete(new UpdateWrapper<SysRoleAcl>().lambda().eq(SysRoleAcl::getRoleId, roleId));
    }
}
