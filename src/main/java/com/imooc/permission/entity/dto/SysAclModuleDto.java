package com.imooc.permission.entity.dto;

import com.imooc.permission.entity.SysAclModule;
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
public class SysAclModuleDto extends SysAclModule {

    private List<SysAclModuleDto> aclModuleList = new ArrayList<>(0);


    private List<SysAclDto>  aclList = new ArrayList<>(0);

    public static SysAclModuleDto adapt(SysAclModule sysAclModule){
        SysAclModuleDto sysAclModuleDto = new SysAclModuleDto();
        BeanUtils.copyProperties(sysAclModule, sysAclModuleDto);
        return sysAclModuleDto;
    }
}
