package com.lbc.user.feign;

import com.lbc.model.article.pojos.ApAuthor;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("leadnews-article")//通过nacos把服务名称为leadnews-article的模块的以下两个接口拿到本模块来使用
public interface ArticleFeign {

    @GetMapping("/api/v1/author/findByUserId/{id}")
    public ApAuthor findByUserId(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/author/save")
    public ResponseResult save(@RequestBody ApAuthor apAuthor);

    @GetMapping("/api/v1/author/one/{id}")
    ApAuthor findById(@PathVariable("id") Integer id);
}