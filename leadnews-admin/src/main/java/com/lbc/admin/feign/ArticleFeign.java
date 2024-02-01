package com.lbc.admin.feign;

import com.lbc.model.article.pojos.ApArticle;
import com.lbc.model.article.pojos.ApArticleConfig;
import com.lbc.model.article.pojos.ApArticleContent;
import com.lbc.model.article.pojos.ApAuthor;
import com.lbc.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("leadnews-article")
public interface ArticleFeign {

    @PostMapping("/api/v1/article_config/save")
    ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);

    @GetMapping("/api/v1/author/findByName/{name}")
    ApAuthor selectAuthorByName(@PathVariable("name") String name);

    @PostMapping("/api/v1/article/save")
    ApArticle saveArticle(ApArticle apArticle);

    @PostMapping("/api/v1/article_content/save")
    ResponseResult saveArticleContent(ApArticleContent apArticleContent);
}