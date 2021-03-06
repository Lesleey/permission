package com.imooc.permission.serivce.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysAclMapper;
import com.imooc.permission.dao.SysDeptMapper;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.serivce.SysAclService;
import com.imooc.permission.serivce.SysDeptService;
import com.imooc.permission.serivce.SysUserService;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.LevelUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept>
 implements SysDeptService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean updateSysDept(SysDept sysDept) {
        SysDept oldDept = baseMapper.selectById(sysDept.getId());
        if(oldDept == null)
            throw new RuntimeException("部门更改参数异常，请稍后重试！");
        if(sysDept.getParentId() != oldDept.getParentId()) {
            SysDept parentDept = baseMapper.selectById(sysDept.getParentId());
            if (parentDept == null)
                sysDept.setLevel(LevelUtil.calculateLevel(null, null));
            else
                sysDept.setLevel(LevelUtil.calculateLevel(parentDept.getLevel(), parentDept.getId()));
            //updateSonLevel(sysDept.getLevel(), sysDept.getId());
            updateAllSonLevelByLevel(oldDept.getLevel() + LevelUtil.SEPARATOR + sysDept.getId(),
                    sysDept.getLevel() + LevelUtil.SEPARATOR + sysDept.getId());
        }
        sysDept.setOperateTime(new Date());
        sysDept.setOperateIp(RequestUtil.getRemoteAddr());
        sysDept.setOperator(ContextUtil.loginUser().getUsername());
        return updateById(sysDept);
    }

    @Override
    public List<SysDept> listDirectSonByParentId(Integer parentId) {
        return baseMapper.selectList(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId, parentId));
    }

    @Override
    public List<SysDept> listOrderByLevelAndSn() {
        List<SysDept> sysDepts = baseMapper.listOrderByLevelAndSn();
        if(sysDepts == null)
            sysDepts = Collections.emptyList();
        return sysDepts;
    }

    @Override
    public Integer recover(SysDept before, SysDept after) {
        if(before == null){
            if(sysUserService.countByDeptId(after.getId()) > 0)
                throw new RuntimeException("该部门下已存在用户，请先删除用户在进行回退操作！");
            if(countByParentId(after.getId()) > 0)
                throw new RuntimeException("该部门下已存在子部门，请先移除子部门在进行回退操作！");
            removeById(after.getId());
        }else if(after == null){
            if(before.getParentId() != 0 &&  getById(before.getParentId()) == null)
                throw new RuntimeException("父部门已被移除，无法进行回退操作！");
            validateDeptName(before.getParentId(), before.getName());
            save(before);
        }else{
            if(before.getParentId() != 0 && getById(before.getParentId()) == null)
                throw new RuntimeException("父部门已被移除，无法进行回退操作！");
            validateDeptName(before.getParentId(), before.getName());
            updateById(before);
        }
        return null;
    }

    @Override
    public Integer countByParentId(Integer parentid) {
        return baseMapper.selectCount(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId, parentid));
    }

    /**
     *  更新所有孩子节点的 level
     * @param parentLevel
     * @param parentId
     */
    private void updateSonLevel(String parentLevel, Integer parentId){
        List<SysDept> sonDepts = listDirectSonByParentId(parentId);
        if(CollectionUtils.isNotEmpty(sonDepts)){
            sonDepts.forEach(sysDept -> {
                sysDept.setLevel(LevelUtil.calculateLevel(parentLevel, parentId));
                updateById(sysDept);
                updateSonLevel(sysDept.getLevel(), sysDept.getId());
            });
        }
    }

    /**
     *   将前缀替换
     * @param beforeLevel
     * @param afterLevel
     */
    private void updateAllSonLevelByLevel(String beforeLevel, String afterLevel){
        baseMapper.updateAllSonLevelByLevel(beforeLevel, afterLevel);
    }

    private void validateDeptName(Integer parentId, String deptName){
        if(count(new QueryWrapper<SysDept>().lambda()
                .eq(SysDept::getParentId, parentId).eq(SysDept::getName, deptName)) > 0 )
            throw new RuntimeException(String.format("该部门下已存在名称为%s的部门!", deptName));
    }

}
