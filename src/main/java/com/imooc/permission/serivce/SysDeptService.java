package com.imooc.permission.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.permission.entity.SysAcl;
import com.imooc.permission.entity.SysDept;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/3-17:58
 * @function
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     *  更新部门
     * @param sysDept
     * @return
     */
    boolean updateSysDept(SysDept sysDept);

    /**
     *  查询当前部门的所有孩子节点
     * @param parentId
     * @return
     */
    List<SysDept>  listDirectSonByParentId(Integer parentId);
}
