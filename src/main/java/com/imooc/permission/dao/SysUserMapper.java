package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.entity.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:42
 * @function
 */
@Mapper
public interface SysUserMapper  extends BaseMapper<SysUser> {

    /**
     *  分页查询用户信息
     * @param userVoPage
     * @param userParam
     * @return
     */
    List<SysUserVo> listPage(Page<SysUserVo> userVoPage, @Param("param") UserParam userParam);
}
