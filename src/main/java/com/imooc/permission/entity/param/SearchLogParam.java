package com.imooc.permission.entity.param;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SearchLogParam implements Serializable {

    //日志类型
    private Integer type; // LogType

    // 更新之前的值
    private String beforeSeg;

    // 更新之后的值
    private String afterSeg;

    private String operator;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fromTime;//yyyy-MM-dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toTime;
}
