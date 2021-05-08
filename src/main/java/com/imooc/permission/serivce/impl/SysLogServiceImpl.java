package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.permission.common.LogType;
import com.imooc.permission.dao.SysLogMapper;
import com.imooc.permission.entity.*;
import com.imooc.permission.entity.param.SearchLogParam;
import com.imooc.permission.serivce.*;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
 implements SysLogService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Override
    public Integer saveDeptLog(SysDept before, SysDept after)  {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_DEPT.getType());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveUserLog(SysUser before, SysUser after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_USER.getType());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveAclModule(SysAclModule before, SysAclModule after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ACL_MODULE.getType());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveAclLog(SysAcl before, SysAcl after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ACL.getType());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveRole(SysRole before, SysRole after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE.getType());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveRoleAcl(int roleId, List<SysRoleAcl> before, List<SysRoleAcl> after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE_ACL.getType());
        sysLog.setTargetId(roleId);
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Integer saveRoleUser(Integer roleId, List<SysRoleUser> before, List<SysRoleUser> after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE_USER.getType());
        sysLog.setTargetId(roleId);
        try {
            sysLog.setOldValue(before == null ? "" : objectMapper.writeValueAsString(before));
            sysLog.setNewValue(after == null ? "" :  objectMapper.writeValueAsString(after));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(RequestUtil.getRemoteAddr());
        sysLog.setOperator(ContextUtil.loginUser().getUsername());
        sysLog.setStatus(1);
        return baseMapper.insert(sysLog);
    }

    @Override
    public Page<SysLog> listPage(Integer pageNo, Integer pageSize, SearchLogParam param) {
        LambdaQueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>().lambda();
        if(param.getType() != null)
            queryWrapper.eq(SysLog::getType, param.getType());
        if(StringUtils.isNotEmpty(param.getAfterSeg()))
            queryWrapper.like(SysLog::getNewValue, "%" + param.getAfterSeg() + "%");
        if(StringUtils.isNotEmpty(param.getBeforeSeg()))
            queryWrapper.like(SysLog::getOldValue, "%" + param.getBeforeSeg() + "%");
        if(StringUtils.isNotEmpty(param.getOperator()))
            queryWrapper.like(SysLog::getOperator, "%" + param.getOperator() + "%");
        if(param.getFromTime() != null)
            queryWrapper.ge(SysLog::getOperateTime, param.getFromTime());
        if(param.getToTime() != null)
            queryWrapper.le(SysLog::getOperateTime, param.getToTime());

        Page<SysLog> resPage = baseMapper.selectPage(new Page<SysLog>(pageNo, pageSize), queryWrapper);
        return resPage;
    }

    @Override
    public Integer coverByLogId(Integer logId) throws IOException {
        SysLog log = getById(logId);
        if(log == null)
            throw new RuntimeException("回退的日志不存在！");
        Integer logType = log.getType();
        String before = log.getOldValue(), after = log.getNewValue();
        if(logType == LogType.TYPE_DEPT.getType()){
            sysDeptService.recover(getTypeByStr(before, SysDept.class), getTypeByStr(after, SysDept.class));
        }else if(logType == LogType.TYPE_USER.getType())
            sysUserService.recover(getTypeByStr(before, SysUser.class), getTypeByStr(after, SysUser.class));
        else if(logType == LogType.TYPE_ROLE_USER.getType())
            sysRoleUserService.recover(log.getTargetId(), getTypeByStr(before, new TypeReference<List<SysRoleUser>>(){}),
                    getTypeByStr(after, new TypeReference<List<SysRoleUser>>(){}));
        else if(logType == LogType.TYPE_ROLE.getType())
            sysRoleService.recover(getTypeByStr(before, SysRole.class),
                    getTypeByStr(after, SysRole.class));
        else if(logType == LogType.TYPE_ROLE_ACL.getType())
            sysRoleAclService.recover(log.getTargetId(), getTypeByStr(before, new TypeReference<List<SysRoleAcl>>(){}),
                    getTypeByStr(before, new TypeReference<List<SysRoleAcl>>(){}));
        else if(logType == LogType.TYPE_ACL.getType())
            sysAclService.recover(getTypeByStr(before, SysAcl.class), getTypeByStr(after, SysAcl.class));
        else if(logType == LogType.TYPE_ACL_MODULE.getType())
            sysAclModuleService.recover(getTypeByStr(before, SysAclModule.class), getTypeByStr(after, SysAclModule.class));
        else
            throw new RuntimeException("无效的日志类型！");
        removeById(logId);
        return 1;
    }

    private <T> T getTypeByStr(String str, TypeReference<T> typeReference) throws IOException {
        if(StringUtils.isEmpty(str))
            return null;
        return objectMapper.readValue(str, typeReference);
    }

    private <T> T getTypeByStr(String str, Class<T> typeReference) throws IOException {
        if(StringUtils.isEmpty(str))
            return null;
        return objectMapper.readValue(str, typeReference);
    }

}
