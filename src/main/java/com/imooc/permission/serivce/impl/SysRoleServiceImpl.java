package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleAclMapper;
import com.imooc.permission.dao.SysRoleMapper;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleAcl;
import com.imooc.permission.serivce.SysRoleAclService;
import com.imooc.permission.serivce.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
 implements SysRoleService {
}
