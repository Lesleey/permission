package com.imooc.permission.entity.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Lesleey
 * @date 2021/5/5-18:38
 * @function
 */
@Data
public class AclModuleParam implements Serializable {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer parentId;
    @NotNull
    private Integer seq;

    @NotNull
    private Integer status;

    private String remark;
}
