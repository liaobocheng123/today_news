package com.heima.article.service;

import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ArticleInfoService {

    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    public ResponseResult loadArticleInfo(ArticleInfoDto dto);

    /**
     * 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢等
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}