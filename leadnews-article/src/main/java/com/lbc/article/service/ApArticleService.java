package com.lbc.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.article.dtos.ArticleHomeDto;
import com.lbc.model.article.pojos.ApArticle;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * 根据参数加载文章列表
     * @param loadType 1为加载更多  2为加载最新   
     * @param dto
     * @return
     */
    ResponseResult load(ArticleHomeDto dto,Short loadType);
}