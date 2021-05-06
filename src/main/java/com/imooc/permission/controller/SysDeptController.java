package com.imooc.permission.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysDept;
import com.imooc.permission.entity.dto.SysDeptDto;
import com.imooc.permission.entity.param.DeptParam;
import com.imooc.permission.serivce.SysDeptService;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.ContextUtil;
import com.imooc.permission.util.LevelUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.*;

/**
 * @author Lesleey
 * @date 2021/5/3-22:42
 * @function
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     *  新增部门
     * @param deptParam
     * @return
     */
    @PostMapping("save")
    public ResponseData<Boolean> save(DeptParam deptParam){
        BeanValidateUtil.validate(deptParam);
        validateDeptName(deptParam.getParentId(), deptParam.getName());
        SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId()).seq(deptParam.getSeq())
                .remark(deptParam.getRemark()).build();
        SysDept parentDept = sysDeptService.getById(sysDept.getParentId());
        if(parentDept == null)
            sysDept.setLevel(LevelUtil.calculateLevel(null, null));
        else
            sysDept.setLevel(LevelUtil.calculateLevel(parentDept.getLevel(), parentDept.getId()));
        sysDept.setOperateTime(new Date());
        sysDept.setOperateIp(RequestUtil.getRemoteAddr());
        sysDept.setOperator(ContextUtil.loginUser().getUsername());
        //todo 操作人
        sysDeptService.save(sysDept);
        return ResponseData.success(true);
    }

    /**
     *   更新部门
     *
     * @param deptParam
     * @return
     */
    @PostMapping("update")
    public ResponseData<Boolean> update(DeptParam deptParam){
        try{
            BeanValidateUtil.validate(deptParam);
            validateDeptName(deptParam.getParentId(), deptParam.getName());
            SysDept sysDept = SysDept.builder().id(deptParam.getId()).name(deptParam.getName()).parentId(deptParam.getParentId())
                    .seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
            sysDept.setOperateTime(new Date());
            sysDept.setOperateIp(RequestUtil.getRemoteAddr());
            sysDept.setOperator(ContextUtil.loginUser().getUsername());
            sysDeptService.updateSysDept(sysDept);
            return ResponseData.success(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("delete/{deptId}")
    public ResponseData<Boolean> removeDept(@PathVariable Integer deptId){
        try{
            if(sysDeptService.count(new QueryWrapper<SysDept>().lambda().eq(SysDept::getParentId, deptId)) > 0)
                throw new RuntimeException("该部门存在子部门，请先移除子部门");
            sysDeptService.removeById(deptId);
            return ResponseData.success(true);
        }catch(Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  查询部门树
     * @return
     */
    @GetMapping("deptTree")
    public ResponseData<List<SysDeptDto>> deptTree(){
        List<SysDept> list = sysDeptService.listOrderByLevelAndSn();
        /*List<SysDept> list = sysDeptService.list(new QueryWrapper<SysDept>().lambda()
                .orderByAsc(sysDept -> sysDept.getLevel().split(LevelUtil.SEPARATOR).length).orderByAsc(SysDept::getSeq));*/
        List<SysDeptDto> rootList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)) {
            Map<Integer, SysDeptDto> treeMap = new HashMap<>();
            list.forEach(sysDept -> {
                SysDeptDto curSysDept = SysDeptDto.adapt(sysDept);
                treeMap.put(curSysDept.getId(), curSysDept);
                if(StringUtils.equalsIgnoreCase(sysDept.getLevel(), LevelUtil.ROOT))
                    rootList.add(curSysDept);
                else
                    treeMap.get(curSysDept.getParentId()).getDeptList().add(curSysDept);
            });

        }
        return ResponseData.success(rootList);

    }


    /**
     *  同一部门下不能存在相同名称的部门
     * @param parentId
     * @param deptName
     */
    private void validateDeptName(Integer parentId, String deptName){
        if(sysDeptService.count(new QueryWrapper<SysDept>().lambda()
                .eq(SysDept::getParentId, parentId).eq(SysDept::getName, deptName)) > 0 )
            throw new RuntimeException(String.format("该部门下已存在名称为%s的部门!", deptName));
    }

}
