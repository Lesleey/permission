package com.imooc.permission.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.permission.dao.SysAclModuleMapper;
import com.imooc.permission.entity.SysAclModule;
import com.imooc.permission.serivce.SysAclModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lesleey
 * @date 2021/5/3-18:00
 * @function
 */
@Service
@Transactional
public class SysAclModuleServiceImpl  extends ServiceImpl<SysAclModuleMapper, SysAclModule>
 implements SysAclModuleService {
}
