package com.imooc.permission.entity.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/3-22:30
 * @function
 */
@Data
public class DeptParam implements Serializable {

    private Integer id;
    private Integer parentId;
    @NotNull(message = "部门名称不能为空！")
    private String name;
    @NotNull(message = "序号不能为空！")
    private Integer seq;
}
