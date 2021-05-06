package com.imooc.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysAclModule;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.AclParam;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.serivce.SysAclModuleService;
import com.imooc.permission.serivce.SysAclService;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/5-18:38
 * @function
 */
@RestController
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysAclModuleService sysAclModuleService;

    /**
     *  分页查询权限
     * @param pageNo
     * @param pageSize
     * @param aclModuleId
     * @return
     */
    @GetMapping("/page/{aclModuleId}")
    public ResponseData<Page<SysAcl>> page(Integer pageNo, Integer pageSize, @PathVariable Integer aclModuleId){
        try{
            Page<SysAcl> pages = sysAclService.listPage(pageNo, pageSize, aclModuleId);
            return ResponseData.success(pages);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  新增权限点
     * @param aclParam
     * @return
     */
    @PostMapping("save")
    public ResponseData<Boolean> save(AclParam aclParam){
        try{
            BeanValidateUtil.validate(aclParam);
            validAclName(aclParam.getAclModuleId(), aclParam.getName());
            validAclModuleIdExist(aclParam.getAclModuleId());
            SysAcl sysAcl = SysAcl.builder().aclModuleId(aclParam.getAclModuleId())
                    .name(aclParam.getName())
                    .seq(aclParam.getSeq()).remark(aclParam.getRemark())
                    .status(aclParam.getStatus()).type(aclParam.getType())
                    .url(aclParam.getUrl()).build();
            sysAcl.setOperateTime(new Date());
            sysAcl.setOperateIp(RequestUtil.getRemoteAddr());
            sysAcl.setOperator(ContextUtil.loginUser().getUsername());
            sysAclService.save(sysAcl);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  新增权限
     * @param aclParam
     * @return
     */
    @PostMapping("update")
    public ResponseData<Boolean> update(AclParam aclParam){
        try{
            BeanValidateUtil.validate(aclParam);
            validAclModuleIdExist(aclParam.getAclModuleId());
            SysAcl sysAcl = SysAcl.builder().id(aclParam.getId()).aclModuleId(aclParam.getAclModuleId())
                    .name(aclParam.getName())
                    .seq(aclParam.getSeq()).remark(aclParam.getRemark())
                    .status(aclParam.getStatus()).type(aclParam.getType())
                    .url(aclParam.getUrl()).build();
            sysAcl.setOperateTime(new Date());
            sysAcl.setOperateIp(RequestUtil.getRemoteAddr());
            sysAcl.setOperator(ContextUtil.loginUser().getUsername());
            sysAcl.setCode(System.currentTimeMillis() + "_" + ContextUtil.loginUser().getId());
            sysAclService.updateById(sysAcl);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  移除权限
     * @param aclId
     * @return
     */
    @GetMapping("delete/{aclId}")
    public ResponseData<Boolean> delete(@PathVariable Integer aclId){
        sysAclService.removeById(aclId);
        return ResponseData.success(true);
    }

    private void validAclName(Integer aclModuleId, String name){
        if(sysAclService.count(new QueryWrapper<SysAcl>().lambda()
        .eq(SysAcl::getAclModuleId, aclModuleId).eq(SysAcl::getName, name)) > 0)
            throw new RuntimeException("该模块下存在相同名称的权限名");

    }

    private void validAclModuleIdExist(Integer aclModuleId){
        if(sysAclModuleService.count(new QueryWrapper<SysAclModule>().lambda()
                .eq(SysAclModule::getId, aclModuleId)) < 1)
            throw new RuntimeException("指定的权限模块已被移除！");
    }


}
