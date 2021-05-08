package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleMapper;
import com.imooc.permission.dao.SysRoleUserMapper;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.serivce.SysLogService;
import com.imooc.permission.serivce.SysRoleService;
import com.imooc.permission.serivce.SysRoleUserService;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser>
 implements SysRoleUserService {
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Integer removeRoleUserByRoleId(Integer roleId) {
        return baseMapper.delete(new UpdateWrapper<SysRoleUser>().lambda().eq(SysRoleUser::getRoleId, roleId));
    }

    @Override
    public Integer changeRoleUsers(List<Integer> userIds, Integer roleId) {
        List<SysRoleUser> before = baseMapper.selectList(new QueryWrapper<SysRoleUser>().lambda().eq(SysRoleUser::getRoleId, roleId));
        int row = removeRoleUserByRoleId(roleId);
        List<SysRoleUser> after = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(userIds)){
            for (Integer userId : userIds) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(userId);
                sysRoleUser.setOperateTime(new Date());
                sysRoleUser.setOperateIp(RequestUtil.getRemoteAddr());
                sysRoleUser.setOperator(ContextUtil.loginUser().getUsername());
                after.add(sysRoleUser);
            }
            saveBatch(after);
        }
        sysLogService.saveRoleUser(roleId, before, after);
        return row;
    }

    @Override
    public boolean isHasRole(Integer roleId, Integer userId) {
        return baseMapper.selectCount(new QueryWrapper<SysRoleUser>().lambda().eq(SysRoleUser::getUserId, userId)
        .eq(SysRoleUser::getRoleId, roleId)) != 0;
    }

    @Override
    public boolean removeByUserId(Integer userid) {
        return baseMapper.delete(new UpdateWrapper<SysRoleUser>().lambda().eq(SysRoleUser::getUserId, userid)) > 0;
    }

    @Override
    public boolean recover(int userid, List<SysRoleUser> before, List<SysRoleUser> after) {
        removeByUserId(userid);
        if(CollectionUtils.isNotEmpty(before)){
            for (SysRoleUser sysRoleUser : before) {
                if(sysRoleService.getById(sysRoleUser.getRoleId()) == null)
                    throw new RuntimeException("部分角色已被移除，无法进行回退操作！");
            }
        }
        saveBatch(before);
        return false;
    }
}
