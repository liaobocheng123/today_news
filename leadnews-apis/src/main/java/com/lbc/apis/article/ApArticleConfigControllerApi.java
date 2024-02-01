package com.lbc.apis.article;

import com.lbc.model.article.pojos.ApArticleConfig;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApArticleConfigControllerApi {

    /**
     * 保存app端文章配置
     * @param apArticleConfig
     * @return
     */
    ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);
}