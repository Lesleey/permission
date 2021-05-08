package com.imooc.permission.common;

import lombok.Data;

/**
 * @author Lesleey
 * @date 2021/5/7-22:03
 * @function
 */
public enum LogType {

    TYPE_DEPT(1),
    TYPE_USER(2),
    TYPE_ACL_MODULE(3),
    TYPE_ACL(4),
    TYPE_ROLE(5),
    TYPE_ROLE_ACL(6),
    TYPE_ROLE_USER(7);

    private Integer type;
    private LogType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return this.type;
    }
}
