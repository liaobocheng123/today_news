package com.lbc.apis.article;

import com.lbc.model.article.pojos.ApArticle;

public interface ApArticleControllerApi {

    /**
     * 保存app文章
     * @param apArticle
     * @return
     */
    ApArticle saveArticle(ApArticle apArticle);
}
