package com.imooc.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.dto.SysAclDto;
import com.imooc.permission.entity.dto.SysAclModuleDto;
import com.imooc.permission.entity.param.RoleParam;
import com.imooc.permission.serivce.*;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lesleey
 * @date 2021/5/6-20:21
 * @function
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysAclModuleService sysAclModuleService;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @GetMapping("list")
    public ResponseData<List<SysRole>> list(){
        return ResponseData.success(sysRoleService.list());
    }
    /**
     *  新增角色
     * @param roleParam
     * @return
     */
    @PostMapping("save")
    public ResponseData<Boolean> save(RoleParam roleParam){
        try{
            BeanValidateUtil.validate(roleParam);
            validRoleName(roleParam.getName());
            SysRole sysRole = SysRole.builder()
                    .name(roleParam.getName()).remark(roleParam.getRemark())
                    .status(roleParam.getStatus()).build();
            sysRole.setOperateTime(new Date());
            sysRole.setOperateIp(RequestUtil.getRemoteAddr());
            sysRole.setOperator(ContextUtil.loginUser().getUsername());
            sysRoleService.save(sysRole);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     *  修改角色
     * @param roleParam
     * @return
     */
    @PostMapping("update")
    public ResponseData<Boolean> update(RoleParam roleParam){
        try{
            BeanValidateUtil.validate(roleParam);
            SysRole sysRole = SysRole.builder().id(roleParam.getId())
                    .name(roleParam.getName()).remark(roleParam.getRemark())
                    .status(roleParam.getStatus()).build();
            sysRole.setOperateTime(new Date());
            sysRole.setOperateIp(RequestUtil.getRemoteAddr());
            sysRole.setOperator(ContextUtil.loginUser().getUsername());
            sysRoleService.updateById(sysRole);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  移除角色
     * @param roleID
     * @return
     */
    @GetMapping("delete/{roleID}")
    public ResponseData<Boolean> delete(@PathVariable Integer roleID){
        try{
            sysRoleService.removeById(roleID);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  查询角色权限树
     * @param roleId
     * @return
     */
    @GetMapping("roleTree/{roleId}")
    public ResponseData<List<SysAclModuleDto>> roleTree(@PathVariable Integer roleId){
        //1. 当前用户具有的权限
        List<SysAcl> userAcls = sysAclService.queryAclByUserId(ContextUtil.loginUser().getId());
        List<Integer> userAclIds = userAcls.stream().map(SysAcl::getId).collect(Collectors.toList());
        //2. 所有的权限
        List<SysAclModuleDto> allAclTree = sysAclModuleService.aclModuleTree();
        //3. 当前角色具有的权限
        List<SysAcl> roleAcls = sysAclService.queryAclByRoleId(Arrays.asList(roleId));

        List<Integer> roleAclIds = roleAcls.stream().map(SysAcl::getId).collect(Collectors.toList());
        renderAclModule(allAclTree, userAclIds, roleAclIds);
        return ResponseData.success(allAclTree);
    }

    /**
     *  dfs权限模块树，进行初始化 cheked、 hasAcl
     * @param aclModuleDtos
     * @param userAclIds
     * @param roleAclIds
     */
    private void renderAclModule(List<SysAclModuleDto> aclModuleDtos, List<Integer> userAclIds, List<Integer> roleAclIds){
        if(CollectionUtils.isEmpty(aclModuleDtos)) return;
        aclModuleDtos.forEach(sysAclModuleDto -> {
            List<SysAcl> sysAcls = sysAclService.listAclsByModuleId(sysAclModuleDto.getId());
            List<SysAclDto> aclList = new ArrayList<>();
            sysAcls.forEach(sysAcl -> {
                SysAclDto adapt = SysAclDto.adapt(sysAcl);
                adapt.setChecked(roleAclIds.contains(sysAcl.getId()));
                adapt.setHasAcl(userAclIds.contains(sysAcl.getId()));
                aclList.add(adapt);
            });
            sysAclModuleDto.setAclList(aclList);
            renderAclModule(sysAclModuleDto.getAclModuleList(), userAclIds, roleAclIds);
        });
    }

    /**
     *  修改角色对应的权限
     * @param roleId
     * @param aclIds
     * @return
     */
    @PostMapping("changeRoleAcl/{roleId}")
    public ResponseData<Boolean> changeRoleAcl(@PathVariable Integer roleId, String aclIds){
        sysRoleAclService.changeRoleAcl(roleId, splitAclIds(aclIds));
        return ResponseData.success(true);
    }
    private void validRoleName(String name){
        if(sysRoleService.count(new QueryWrapper<SysRole>().lambda()
        .eq(SysRole::getName, name)) > 0)
            throw new RuntimeException("角色名称已存在！");
    }

    private List<Integer> splitAclIds(String str){
        if(StringUtils.isEmpty(str))
            return Collections.emptyList();
        String[] split = StringUtils.split(str, ",");
        return Arrays.stream(split).map(Integer::new).collect(Collectors.toList());
    }

    /**
     *  查询角色和用户的对应关系
     * @param roleId
     * @return
     */
    @GetMapping("roleUsers/{roleId}")
    public ResponseData<?> roleUsersMap(@PathVariable Integer roleId){
        try{
            List<SysUser> selectedList = sysUserService.getUsersByRoleId(roleId);
            List<SysUser> allUsers = sysUserService.listUserOrderByDeptSeqAndUserSeq();
            List<SysUser> unselectedList = allUsers.stream().filter(sysUser -> !selectedList.contains(sysUser)).collect(Collectors.toList());
            Map<String, List<SysUser>> res = new HashMap<>();
            res.put("selected", selectedList);
            res.put("unselected", unselectedList);
            return ResponseData.success(res);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  修改角色和用户的对应关系
     * @param roleId
     * @return
     */
    @PostMapping("/changeRoleUsers/{roleId}")
    public ResponseData<Boolean> changeRoleUSERS(@PathVariable Integer roleId ,String userIds){
        try{
            if(!sysRoleUserService.isHasRole(roleId, ContextUtil.loginUser().getId()))
                throw new RuntimeException("对不起，您没有该权限！");
            sysRoleUserService.changeRoleUsers(splitAclIds(userIds), roleId);
            return ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

}
