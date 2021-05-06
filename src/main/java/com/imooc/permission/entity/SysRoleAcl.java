package com.imooc.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/3-17:25
 * @function
 */
@Data
@TableName("sys_role_acl")
public class SysRoleAcl implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private Integer roleId;
    @TableField
    private Integer aclId;
    @TableField
    private String operator;
    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;
    @TableField
    private String operateIp;
}
