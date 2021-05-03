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
import com.imooc.permission.util.LevelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    public boolean updateSysDept(SysDept sysDept) {
        SysDept oldDept = baseMapper.selectById(sysDept);
        if(oldDept == null)
            throw new RuntimeException("部门更改参数异常，请稍后重试！");
        if(sysDept.getParentId() != oldDept.getParentId()) {
            SysDept parentDept = baseMapper.selectById(sysDept.getParentId());
            if (parentDept == null)
                sysDept.setLevel(LevelUtil.calculateLevel(null, null));
            else
                sysDept.setLevel(LevelUtil.calculateLevel(parentDept.getLevel(), parentDept.getId()));
            //updateSonLevel(sysDept.getLevel(), sysDept.getId());
            updateAllSonLevelByLevel(oldDept.getLevel(), sysDept.getLevel());
        }
        return updateById(sysDept);
    }

    @Override
    public List<SysDept> listDirectSonByParentId(Integer parentId) {
        return baseMapper.selectList(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId, parentId));
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

}
