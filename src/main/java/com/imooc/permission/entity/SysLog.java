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
 * @date 2021/5/3-17:28
 * @function
 */
@Data
@TableName("sys_log")
public class SysLog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private Integer type;
    @TableField
    private Integer targetId;
    @TableField
    private String oldValue;
    @TableField
    private String newValue;
    @TableField
    private Integer status;
    @TableField
    private String operator;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField
    private Date operateTime;
    @TableField
    private String operateIp;
}
