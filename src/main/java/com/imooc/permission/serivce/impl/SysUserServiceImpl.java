package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysRoleUserMapper;
import com.imooc.permission.dao.SysUserMapper;
import com.imooc.permission.entity.SysRoleUser;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.serivce.SysRoleUserService;
import com.imooc.permission.serivce.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
 implements SysUserService {
}
