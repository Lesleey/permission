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
 * @date 2021/5/3-17:34
 * @function
 */
@Data
@TableName("sys_acl")
public class SysAcl implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String code;
    @TableField
    private String name;
    @TableField
    private String aclModuleId;
    @TableField
    private String url;
    @TableField
    private Integer type;
    @TableField
    private Integer seq;
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
