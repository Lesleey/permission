package com.imooc.permission.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysLog;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.SearchLogParam;
import com.imooc.permission.serivce.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lesleey
 * @date 2021/5/7-22:40
 * @function
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     *  分页查询日志数据
     * @param pageNo
     * @param pageSize
     * @param searchLogParam
     * @return
     */
    @PostMapping("/page/{pageNo}/{pageSize}")
    public ResponseData<Page<SysLog>> page(@PathVariable Integer pageNo, @PathVariable Integer pageSize, SearchLogParam searchLogParam){
        try{
            Page<SysLog> pages = sysLogService.listPage(pageNo, pageSize, searchLogParam);
            return ResponseData.success(pages);
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     *  还原某个操作
     * @param logId
     * @return
     */
    @GetMapping("recover/{logId}")
    public ResponseData<Boolean> recover(@PathVariable Integer logId){
        try{
            sysLogService.coverByLogId(logId);
            return ResponseData.success(true);
        }catch(Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

}
