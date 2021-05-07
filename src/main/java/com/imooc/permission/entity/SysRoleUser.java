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
 * @date 2021/5/3-17:23
 * @function
 */
@Data
@TableName("sys_role_user")
public class SysRoleUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private Integer roleId;
    @TableField
    private Integer userId;
    @TableField
    private String operate;
    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;
    @TableField
    private String operateIp;

    @TableField
    private String operator;
}
