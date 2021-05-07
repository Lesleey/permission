package com.imooc.permission.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
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

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/WEB-INFO/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/WEB-INFO/css/");
        registry.addResourceHandler("/bootstrap3.3.5/**").addResourceLocations("classpath:/WEB-INFO/bootstrap3.3.5/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/WEB-INFO/assets/");
        registry.addResourceHandler("/ztree/**").addResourceLocations("classpath:/WEB-INFO/ztree/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver viewResolver(WebMvcProperties webMvcProperties){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix(webMvcProperties.getView().getPrefix());
        internalResourceViewResolver.setSuffix(webMvcProperties.getView().getSuffix());
        return internalResourceViewResolver;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
