package com.lbc.apis.article;

import com.lbc.model.article.pojos.ApArticleContent;
import com.lbc.model.common.dtos.ResponseResult;

public interface ApArticleContentControllerApi {

    /**
     * 保存app端文章内容
     * @param apArticleContent
     * @return
     */
    ResponseResult saveArticleContent(ApArticleContent apArticleContent);
}