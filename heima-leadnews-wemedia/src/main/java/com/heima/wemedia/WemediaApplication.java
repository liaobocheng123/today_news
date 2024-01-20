package com.heima.wemedia;

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
@MapperScan("com.heima.wemedia.mapper")
@EnableDiscoveryClient
@ServletComponentScan//开启本模块filter.WmTokenFilter的功能：拦截所有调用本模块的方法，给一个线程放上用户信息（用户信息由经过网关时给的userId所得）
public class WemediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }


    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
