package com.heima.common.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;
import java.util.Collections;

@Data
@Configuration
@PropertySource("classpath:mongo.properties")
@ConfigurationProperties(prefix="mongo")
public class MongoDBconfigure {

    private String host;
    private Integer port;
    private String dbname;//需要链接存储的数据库
    private String username;
    private String password;
    private String authenticationDatabase;//认证数据库

    @Bean
    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(getSimpleMongoDbFactory());
    }

    //mongodb无需认证
    /*public SimpleMongoDbFactory getSimpleMongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(host, port), dbname);
    }*/

    public SimpleMongoDbFactory getSimpleMongoDbFactory() {

        // Set credentials
        MongoCredential credential = MongoCredential.createCredential(username, authenticationDatabase, password.toCharArray());
        ServerAddress serverAddress = new ServerAddress(host, port);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress, Collections.singletonList(credential));

        // Mongo DB Factory
        return new SimpleMongoDbFactory(mongoClient, dbname);
    }


}