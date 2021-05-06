package com.imooc.permission.entity.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/6-19:29
 * @function
 */
@Data
public class AclParam implements Serializable {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer aclModuleId;
    @NotNull
    private String url;
    @NotNull
    private Integer type;
    @NotNull
    private Integer seq;
    @NotNull
    private Integer status;
    private String remark;
}
