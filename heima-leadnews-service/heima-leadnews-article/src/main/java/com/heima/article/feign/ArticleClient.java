package com.heima.article.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.service.ApArticleConfigService;
import com.heima.article.service.ApArticleService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.common.dtos.ResponseResult;
import com.lbc.apis.article.IArticleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleClient implements IArticleClient {
    @Autowired
    private ApArticleService apArticleService;
    @Autowired
    private ApArticleConfigService apArticleConfigService;
    @Override
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }

    @GetMapping("/api/v1/article/findArticleConfigByArticleId/{articleId}")
    @Override
    public ResponseResult findArticleConfigByArticleId(@PathVariable("articleId") Long articleId) {
        ApArticleConfig apArticleConfig = apArticleConfigService.getOne(Wrappers.<ApArticleConfig>lambdaQuery().eq(ApArticleConfig::getArticleId, articleId));
        return ResponseResult.okResult(apArticleConfig);
    }
}
