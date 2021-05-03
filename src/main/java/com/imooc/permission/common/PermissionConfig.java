package com.imooc.permission.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author Lesleey
 * @date 2021/5/3-19:59
 * @function
 */
@Configuration
public class PermissionConfig extends WebMvcConfigurationSupport {


    @Bean
    public View jsonView(){
        return new MappingJackson2JsonView();
    }
}
