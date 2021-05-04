package com.imooc.permission.entity.dto;

import com.imooc.permission.entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/4-0:03
 * @function
 */
@Data
public class SysDeptDto  extends SysDept {

    private List<SysDeptDto> deptList = new ArrayList<>(0);

    public static SysDeptDto adapt(SysDept sysDept){
        SysDeptDto sysDeptDto = new SysDeptDto();
        BeanUtils.copyProperties(sysDept, sysDeptDto);
        return sysDeptDto;
    }
}
