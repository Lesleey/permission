package com.imooc.permission.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Lesleey
 * @date 2021/5/7-20:28
 * @function
 */
@Configuration
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthConfig {
    private List<String> whiteList;

    private String noAuthUrl;

}
