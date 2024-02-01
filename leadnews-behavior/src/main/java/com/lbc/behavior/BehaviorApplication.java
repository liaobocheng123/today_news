package com.lbc.behavior;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * TODO
 *
 * @author QLP
 * @version 1.0
 * @date 2021/8/7 13:48
 */
//@SpringBootApplication
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan("com.lbc.behavior.mapper")
@EnableDiscoveryClient
@ServletComponentScan
public class BehaviorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehaviorApplication.class,args);
    }


    //没有实现文字收藏功能，实现的话可以仿照@RequestMapping("/api/v1/un_likes_behavior")编写

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
