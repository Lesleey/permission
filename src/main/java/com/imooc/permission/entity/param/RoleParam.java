package com.imooc.permission.entity.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/6-20:20
 * @function
 */
@Data
public class RoleParam implements Serializable {

    private Integer id;
    @NotNull(message = "角色名称不能为空！")
    private String name;
    @NotNull(message = "角色状态不能为空！")
    private Integer status;
    private String remark;
}
