package com.imooc.permission.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysAclModule;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.entity.dto.SysAclModuleDto;
import com.imooc.permission.entity.dto.SysDeptDto;
import com.imooc.permission.entity.param.AclModuleParam;
import com.imooc.permission.entity.param.DeptParam;
import com.imooc.permission.serivce.SysAclModuleService;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.LevelUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Lesleey
 * @date 2021/5/5-18:37
 * @function
 */
@RestController
@RequestMapping("sys/aclModule")
public class SysAclModuleController {
    @Autowired
    private SysAclModuleService sysAclModuleService;
    /**
     *  新增权限模块
     * @param aclModuleParam
     * @return
     */
    @PostMapping("save")
    public ResponseData<Boolean> save(AclModuleParam aclModuleParam){
        BeanValidateUtil.validate(aclModuleParam);
        validateModuleName(aclModuleParam.getParentId(), aclModuleParam.getName());
        SysAclModule sysAclModule = SysAclModule.builder().name(aclModuleParam.getName()).parentId(aclModuleParam.getParentId()).seq(aclModuleParam.getSeq())
                .remark(aclModuleParam.getRemark()).status(aclModuleParam.getStatus()).build();
        SysAclModule parentAclModule = sysAclModuleService.getById(aclModuleParam.getParentId());

        if(parentAclModule == null)
            sysAclModule.setLevel(LevelUtil.calculateLevel(null, null));
        else
            sysAclModule.setLevel(LevelUtil.calculateLevel(parentAclModule.getLevel(), parentAclModule.getId()));
        sysAclModule.setOperateTime(new Date());
        sysAclModule.setOperateIp(RequestUtil.getRemoteAddr());
        sysAclModule.setOperator(ContextUtil.loginUser().getUsername());
        sysAclModuleService.save(sysAclModule);
        return ResponseData.success(true);
    }


    /**
     *   更新权限模块
     *
     * @param aclModuleParam
     * @return
     */
    @PostMapping("update")
    public ResponseData<Boolean> update(AclModuleParam aclModuleParam){
        try{
            BeanValidateUtil.validate(aclModuleParam);
            SysAclModule sysAclModule = SysAclModule.builder().id(aclModuleParam.getId()).name(aclModuleParam.getName()).parentId(aclModuleParam.getParentId()).seq(aclModuleParam.getSeq())
                    .remark(aclModuleParam.getRemark()).status(aclModuleParam.getStatus()).build();
            sysAclModule.setOperateTime(new Date());
            sysAclModule.setOperateIp(RequestUtil.getRemoteAddr());
            sysAclModule.setOperator(ContextUtil.loginUser().getUsername());
            sysAclModuleService.updateSysAclModule(sysAclModule);
            return ResponseData.success(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }
    }



    private void validateModuleName(Integer parentId, String name) {
        if( sysAclModuleService.count(new QueryWrapper<SysAclModule>().lambda()
        .eq(SysAclModule::getParentId, parentId).eq(SysAclModule::getName, name)) > 0)
            throw  new RuntimeException("该模快下已存在相同名称的权限模块");
    }

    @GetMapping("delete/{aclModuleId}")
    public ResponseData<Boolean> removeAclModule(@PathVariable Integer aclModuleId){
        try{
            if(sysAclModuleService.count(new QueryWrapper<SysAclModule>().lambda().eq(SysAclModule::getParentId, aclModuleId)) > 0)
                throw new RuntimeException("该模块存在子模块，请先移除子模块");
            sysAclModuleService.removeById(aclModuleId);
            return ResponseData.success(true);
        }catch(Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  查询权限模块树
     * @return
     */
    @GetMapping("aclModuleTree")
    public ResponseData<List<SysAclModuleDto>> aclModuleTree(){
        return ResponseData.success(sysAclModuleService.aclModuleTree());

    }
}
