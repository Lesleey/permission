package com.imooc.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.permission.common.config.UserConfig;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.serivce.SysDeptService;
import com.imooc.permission.serivce.SysLogService;
import com.imooc.permission.serivce.SysUserService;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/4-22:10
 * @function
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysLogService sysLogService;


    @GetMapping("/page/{deptId}")
    public ResponseData<Page<SysUser>> page(Integer pageNo, Integer pageSize, @PathVariable Integer deptId){
        try{
            Page<SysUser> pages = sysUserService.listPage(pageNo, pageSize, deptId);
            return ResponseData.success(pages);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *   新增用户
     * @param userParam
     * @return
     */
    @PostMapping("save")
    public ResponseData<Boolean> saveUser(UserParam userParam){
        try{
            BeanValidateUtil.validate(userParam);
            validEmailExist(userParam.getMail());
            validTeleExist(userParam.getTelephone());
            validDeptExist(userParam.getDeptId());
            SysUser sysUser = SysUser.builder().deptId(userParam.getDeptId()).mail(userParam.getMail())
                    .username(userParam.getUsername()).password(DigestUtils.md5Hex(userConfig.getInitPassword())).remark(userParam.getRemark())
                    .telephone(userParam.getTelephone()).status(userParam.getStatus())
                    .build();
            sysUser.setOperateTime(new Date());
            sysUser.setOperateIp(RequestUtil.getRemoteAddr());
            sysUser.setOperator(ContextUtil.loginUser().getUsername());
            sysUserService.save(sysUser);
            sysLogService.saveUserLog(null, sysUser);
            return  ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     *   更新用户
     * @param userParam
     * @return
     */
    @PostMapping("update")
    public ResponseData<Boolean> updateUser(UserParam userParam){
        try{
            BeanValidateUtil.validate(userParam);
            validDeptExist(userParam.getDeptId());
            SysUser sysUser = SysUser.builder().id(userParam.getId()).deptId(userParam.getDeptId()).mail(userParam.getMail())
                    .username(userParam.getUsername()).password(Md5Crypt.md5Crypt(userParam.getPassword().getBytes())).remark(userParam.getRemark())
                    .telephone(userParam.getTelephone()).status(userParam.getStatus())
                    .build();
            sysUser.setOperateTime(new Date());
            sysUser.setOperateIp(RequestUtil.getRemoteAddr());
            sysUser.setOperator(ContextUtil.loginUser().getUsername());
            sysLogService.saveUserLog(sysUserService.getById(userParam.getId()), sysUser);
            sysUserService.updateById(sysUser);
            return  ResponseData.success(true);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("info/{id}")
    public ResponseData<SysUser> info(@PathVariable Integer id){
        return ResponseData.success(sysUserService.getById(id));
    }



    private void validDeptExist(Integer deptId) {
        if(sysDeptService.count(new QueryWrapper<SysDept>().lambda().eq(SysDept::getId, deptId)) < 1)
            throw new RuntimeException("该单位被移除！");
    }

    private void validEmailExist(String email){
        if(sysUserService.count(new QueryWrapper<SysUser>().lambda().eq(SysUser::getMail, email)) > 0)
            throw new RuntimeException("邮箱已被注册！");
    }

    private void validTeleExist(String tel){
        if(sysUserService.count(new QueryWrapper<SysUser>().lambda().eq(SysUser::getTelephone, tel)) > 0)
            throw new RuntimeException("手机号已被注册！");
    }
}
