package com.imooc.permission.controller;

import com.alibaba.druid.util.StringUtils;
import com.imooc.permission.common.ResponseData;
import com.imooc.permission.entity.SysUser;
import com.imooc.permission.entity.param.LoginParam;
import com.imooc.permission.entity.param.UserParam;
import com.imooc.permission.serivce.SysUserService;
import com.imooc.permission.util.BeanValidateUtil;
import com.imooc.permission.util.RequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lesleey
 * @date 2021/5/5-12:25
 * @function
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    @PostMapping("/login")
    public ModelAndView login(LoginParam loginParam){
        ModelAndView modelAndView = new ModelAndView();
        String error = null;
        try{
            BeanValidateUtil.validate(loginParam);
            SysUser sysUser = sysUserService.selectSysUserByEmailOrTel(loginParam.getPrincipal());
            if(sysUser == null)
                throw new RuntimeException("邮箱或者手机号不存在！");
            if(!StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(loginParam.getPassword()), sysUser.getPassword()))
                throw new RuntimeException("密码错误!");
            if(!StringUtils.equalsIgnoreCase(sysUser.getStatus(), "1"))
                throw new RuntimeException("账户已冻结！");
            HttpServletRequest request = RequestUtil.getRequest();
            modelAndView.addObject("user", sysUser);
            modelAndView.addObject("ret", request.getParameter("ret"));
            request.getSession().setAttribute("user", sysUser);
            modelAndView.setViewName("forward:/pages/admin.jsp");
        }catch (Exception e){
            error = e.getMessage();
            modelAndView.addObject("error", error);
            modelAndView.setViewName("forward:/signin.jsp");
        }
        return modelAndView;
    }

    @GetMapping("logout")
    public void loginout(HttpServletRequest request, HttpServletResponse response){
            request.getSession().invalidate();
        try {
            response.sendRedirect("/signin.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
