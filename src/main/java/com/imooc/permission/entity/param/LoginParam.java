package com.imooc.permission.entity.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lesleey
 * @date 2021/5/5-12:32
 * @function
 */
@Data
public class LoginParam implements Serializable {
    @NotNull
    @NotEmpty
    private String principal;
    @NotNull
    @NotEmpty
    private String password;
}
