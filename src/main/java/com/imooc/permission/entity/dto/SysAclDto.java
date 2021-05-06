package com.imooc.permission.entity.dto;

import com.imooc.permission.entity.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Lesleey
 * @date 2021/5/6-21:04
 * @function
 */
@Data
public class SysAclDto  extends SysAcl {
    /**
     *  当前角色是否包含该权限
     */
    private boolean checked;

    /**
     *  当前登录人是否有权限操作该权限（用户只能分配它所具有的权限）
     */
    private boolean hasAcl;

    public static SysAclDto adapt(SysAcl sysAcl){
        SysAclDto sysAclDto = new SysAclDto();
        BeanUtils.copyProperties(sysAcl, sysAclDto);
        return sysAclDto;
    }

}
