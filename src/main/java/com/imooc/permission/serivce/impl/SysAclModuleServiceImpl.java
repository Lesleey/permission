package com.imooc.permission.serivce.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysAclModuleMapper;
import com.imooc.permission.entity.SysAclModule;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.entity.dto.SysAclModuleDto;
import com.imooc.permission.serivce.SysAclModuleService;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.LevelUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysAclModuleServiceImpl  extends ServiceImpl<SysAclModuleMapper, SysAclModule>
 implements SysAclModuleService {
    @Override
    public boolean updateSysAclModule(SysAclModule sysAclModule) {
        SysAclModule oldAclModule = baseMapper.selectById(sysAclModule.getId());
        if(oldAclModule == null)
            throw new RuntimeException("部门更改参数异常，请稍后重试！");
        if(oldAclModule.getParentId() != oldAclModule.getParentId()) {
            SysAclModule parentAclModule = baseMapper.selectById(sysAclModule.getParentId());
            if (parentAclModule == null)
                sysAclModule.setLevel(LevelUtil.calculateLevel(null, null));
            else
                sysAclModule.setLevel(LevelUtil.calculateLevel(parentAclModule.getLevel(), parentAclModule.getId()));
            //updateSonLevel(sysDept.getLevel(), sysDept.getId());
            updateAllSonLevelByLevel(oldAclModule.getLevel() + LevelUtil.SEPARATOR + sysAclModule.getId(),
                    sysAclModule.getLevel() + LevelUtil.SEPARATOR + sysAclModule.getId());
        }
        sysAclModule.setOperateTime(new Date());
        sysAclModule.setOperateIp(RequestUtil.getRemoteAddr());
        sysAclModule.setOperator(ContextUtil.loginUser().getUsername());
        return updateById(sysAclModule);
    }

    @Override
    public List<SysAclModule> listOrderByLevelAndSn() {
        return baseMapper.listOrderByLevelAndSn();
    }

    @Override
    public List<SysAclModuleDto> aclModuleTree() {
        List<SysAclModule> list = baseMapper.listOrderByLevelAndSn();
        /*List<SysDept> list = sysDeptService.list(new QueryWrapper<SysDept>().lambda()
                .orderByAsc(sysDept -> sysDept.getLevel().split(LevelUtil.SEPARATOR).length).orderByAsc(SysDept::getSeq));*/
        List<SysAclModuleDto> rootList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)) {
            Map<Integer, SysAclModuleDto> treeMap = new HashMap<>();
            list.forEach(sysAclModule -> {
                SysAclModuleDto sysAclModuleDto = SysAclModuleDto.adapt(sysAclModule);
                treeMap.put(sysAclModuleDto.getId(), sysAclModuleDto);
                if(StringUtils.equalsIgnoreCase(sysAclModule.getLevel(), LevelUtil.ROOT))
                    rootList.add(sysAclModuleDto);
                else
                    treeMap.get(sysAclModuleDto.getParentId()).getAclModuleList().add(sysAclModuleDto);
            });

        }
        return rootList;
    }

    /**
     *  更新所有孩子节点的level
     * @param beforeLevel
     * @param afterLevel
     */
    private void updateAllSonLevelByLevel(String beforeLevel, String afterLevel) {
        baseMapper.updateAllSonLevelByLevel(beforeLevel, afterLevel);
    }
}
