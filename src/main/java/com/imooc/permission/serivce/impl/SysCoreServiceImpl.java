package com.imooc.permission.serivce.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.serivce.SysAclService;
import com.imooc.permission.serivce.SysCoreService;
import com.imooc.permission.util.ContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lesleey
 * @date 2021/5/7-20:42
 * @function
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private PathMatcher pathMatcher;

    @Override
    public boolean hasUrlAcl(String url) {
        /*if(StringUtils.equalsIgnoreCase("Lesleey", ContextUtil.loginUser().getUsername()))
            return true;*/
        //1. 查询出所有状态有效的权限点
        List<SysAcl> allAcls = sysAclService.list(new QueryWrapper<SysAcl>().lambda().eq(SysAcl::getStatus, 1));
        //2. 如果没有设置权限点的话，则表示所有的接口对所有的用户都开放
        if(CollectionUtils.isEmpty(allAcls))
            return true;
        //3. 找出用户具有的权限点
        List<SysAcl> sysAcls = sysAclService.queryAclByUserId(ContextUtil.loginUser().getId());
       /* if(CollectionUtils.isEmpty(sysAcls))
            return false;*/
        List<Integer> userAclIds = sysAcls.stream().filter(sysAcl -> sysAcl.getStatus() == 1).map(SysAcl::getId).collect(Collectors.toList());


        // 默认规则为只要有一个权限点满足就允许访问
        boolean isFindMatchFromAll = false;
        for (SysAcl allAcl : allAcls) {
            if(allAcl == null) continue;
            if(pathMatcher.match(allAcl.getUrl(), url)){
                isFindMatchFromAll = true;
                if(userAclIds.contains(allAcl.getId()))
                    return  true;
            }
        }
        return !isFindMatchFromAll;
    }
}
