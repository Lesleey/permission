package com.imooc.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/3-17:26
 * @function
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class SysRole  implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String name;
    @TableField
    private Integer status;
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
