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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/3-17:30
 * @function
 */
@TableName("sys_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDept implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String name;
    @TableField
    private Integer parentId;
    @TableField
    private String level;
    @TableField
    private Integer seq;
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
