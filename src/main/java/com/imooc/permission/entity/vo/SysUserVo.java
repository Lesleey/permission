package com.imooc.permission.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/5-14:37
 * @function
 */
@Data
public class SysUserVo implements Serializable {
    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private Integer deptId;

    private String status;

    private String remark;
}
