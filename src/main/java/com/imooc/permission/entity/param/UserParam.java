package com.imooc.permission.entity.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/4-22:10
 * @function
 */
@Data
public class UserParam implements Serializable {
    private Integer id;

    @NotNull(message = "用户名称不能为空！")
    private String username;

    @NotNull(message = "电话不能为空！")
    private String telephone;

    @NotNull(message = "电话不能为空！")
    private String mail;

    @NotNull(message = "必须指定用户的部门!")
    private Integer deptId;

    private String password;

    @NotNull(message = "用户的状态不能为空")
    private String status;

    private String remark;
}