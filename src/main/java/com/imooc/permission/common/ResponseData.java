package com.imooc.permission.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lesleey
 * @date 2021/5/3-20:20
 * @function
 */
@Data
public class ResponseData <T>{
    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseData success(T data){
        ResponseData responseData = new ResponseData();
        responseData.code = 200;
        responseData.msg = "success";
        responseData.data = data;
        return responseData;
    }

    public static <T> ResponseData error(String msg){
        ResponseData responseData = new ResponseData();
        responseData.code = 500;
        responseData.msg = msg;
        return responseData;
    }

    public static <T> ResponseData error(){
        return error("System error");
    }

    public Map<String, Object> toMap(){
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("msg", msg);
        res.put("data", data);
        return res;
    }
}
