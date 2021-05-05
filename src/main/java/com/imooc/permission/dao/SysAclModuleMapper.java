package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.permission.entity.SysAclModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:55
 * @function
 */
@Mapper
public interface SysAclModuleMapper extends BaseMapper<SysAclModule> {
    Integer updateAllSonLevelByLevel(@Param("beforeLevel") String beforeLevel, @Param("afterLevel") String afterLevel);

    List<SysAclModule> listOrderByLevelAndSn();

}
