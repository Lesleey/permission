package com.imooc.permission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.permission.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Lesleey
 * @date 2021/5/3-17:56
 * @function
 */
@Mapper
public interface SysDeptMapper  extends BaseMapper<SysDept> {

    /**
     *  将前缀为 beforeLevel 替换为 afterLevel
     * @param beforeLevel
     * @param afterLevel
     * @return
     */
    Integer updateAllSonLevelByLevel(@Param("beforeLevel") String beforeLevel,@Param("afterLevel") String afterLevel);
}
