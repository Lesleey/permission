package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleMapper;
import com.imooc.permission.dao.SysRoleUserMapper;
import com.imooc.permission.entity.SysRole;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.serivce.SysRoleService;
import com.imooc.permission.serivce.SysRoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser>
 implements SysRoleUserService {
}
