package com.lbc.apis.article;

import com.lbc.model.article.dtos.ArticleInfoDto;
import com.lbc.model.common.dtos.ResponseResult;

public interface ArticleInfoControllerApi {

    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    public ResponseResult loadArticleInfo(ArticleInfoDto dto);

    /**
     * 加载文章详情的行为内容
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior( ArticleInfoDto dto);
}