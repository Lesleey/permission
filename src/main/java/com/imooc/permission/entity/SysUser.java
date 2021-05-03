package com.imooc.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/3-16:38
 * @function
 */
@TableName("sys_user")
@Data
public class SysUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    @NotNull
    private String username;
    @TableField
    private String telephone;
    @TableField
    private String mail;
    @TableField
    private String password;
    @TableField
    private Integer deptId;
    @TableField
    private String status;
    @TableField
    private String remark;
    @TableField
    private String operator;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField
    private Date operateTime;
    @TableField
    private String operateIp;

}
