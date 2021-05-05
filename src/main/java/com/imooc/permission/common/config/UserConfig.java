package com.imooc.permission.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lesleey
 * @date 2021/5/4-22:19
 * @function
 *
 */
@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {
    private String initPassword;
}
