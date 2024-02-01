package com.lbc.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.lbc.common.jackson","com.lbc.common.aliyun","com.lbc.common.exception"})
public class InitConfig {
}
