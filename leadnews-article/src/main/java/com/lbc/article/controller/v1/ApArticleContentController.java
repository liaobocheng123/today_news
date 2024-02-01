package com.lbc.article.controller.v1;

import com.lbc.apis.article.ApArticleContentControllerApi;
import com.lbc.article.service.ApArticleContentService;
import com.lbc.model.article.pojos.ApArticleContent;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article_content")
public class ApArticleContentController implements ApArticleContentControllerApi {

    @Autowired
    private ApArticleContentService apArticleContentService;

    @PostMapping("/save")
    @Override
    public ResponseResult saveArticleContent(@RequestBody ApArticleContent apArticleContent) {
        apArticleContentService.save(apArticleContent);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
