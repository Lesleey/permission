package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysAclMapper;
import com.imooc.permission.dao.SysDeptMapper;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.serivce.SysAclService;
import com.imooc.permission.serivce.SysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept>
 implements SysDeptService {
}
